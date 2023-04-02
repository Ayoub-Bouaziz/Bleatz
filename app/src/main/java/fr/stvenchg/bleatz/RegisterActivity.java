package fr.stvenchg.bleatz;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.IOException;

import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.RegistrationRequest;
import fr.stvenchg.bleatz.api.RegistrationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText mFirstnameEditText;
    private TextInputEditText mLastnameEditText;
    private TextInputEditText mEmailEditText;
    private TextInputEditText mPasswordEditText;
    private TextInputEditText mConfirmPasswordEditText;
    private Button mRegisterSendButton;
    private TextView mLaterTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirstnameEditText = findViewById(R.id.register_edittext_firstname);
        mLastnameEditText = findViewById(R.id.register_edittext_lastname);
        mEmailEditText = findViewById(R.id.register_edittext_email);
        mPasswordEditText = findViewById(R.id.register_edittext_password);
        mConfirmPasswordEditText = findViewById(R.id.register_edittext_confirmpassword);
        mRegisterSendButton = findViewById(R.id.register_button_register);
        mLaterTextView = findViewById(R.id.register_textview_later);

        mRegisterSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String firstname = mFirstnameEditText.getText().toString().trim();
        String lastname = mLastnameEditText.getText().toString().trim();
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();
        String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RegistrationResponse> call = apiInterface.registerUser(new RegistrationRequest(firstname, lastname, email, password, confirmPassword));
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    RegistrationResponse registrationResponse = response.body();
                    if (registrationResponse != null) {
                        if (registrationResponse.isSuccess()) {
                            Toast.makeText(RegisterActivity.this, registrationResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            // Redirection vers l'activité de validation de l'email
                        } else {
                            Toast.makeText(RegisterActivity.this, registrationResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Une erreur est survenue.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        RegistrationResponse errorResponse = new Gson().fromJson(response.errorBody().string(), RegistrationResponse.class);
                        Toast.makeText(RegisterActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Une erreur est survenue.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("RegisterActivity", "onFailure: " + t.getMessage());
            }
        });
    }
}
