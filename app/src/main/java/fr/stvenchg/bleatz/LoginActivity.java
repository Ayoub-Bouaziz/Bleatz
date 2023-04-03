package fr.stvenchg.bleatz;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.login.LoginRequest;
import fr.stvenchg.bleatz.api.login.LoginResponse;
import fr.stvenchg.bleatz.api.register.RegistrationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText mEmailEditText;
    private TextInputEditText mPasswordEditText;
    private TextView mForgotTextView;
    private Button mLoginSendButton;
    private TextView mRegisterTextView;
    private TextView mLaterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailEditText = findViewById(R.id.login_edittext_email);
        mPasswordEditText = findViewById(R.id.login_edittext_password);
        mForgotTextView = findViewById(R.id.login_textview_forgot);
        mLoginSendButton = findViewById(R.id.login_button_login);
        mRegisterTextView = findViewById(R.id.login_textview_register);
        mLaterTextView = findViewById(R.id.login_textview_later);

        // Vérification des champs du formulaire
        mEmailEditText.addTextChangedListener(textWatcher);
        mPasswordEditText.addTextChangedListener(textWatcher);

        // Désactiver le bouton de connexion
        mLoginSendButton.setEnabled(false);

        mRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        mLoginSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        // Vérifier la présence de "@" et "."
        return email.contains("@") && email.contains(".");
    }
    private boolean fieldsCheck() {
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();

        return isValidEmail(email) &&
                !TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(password) &&
                password.length() > 6;
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            mLoginSendButton.setEnabled(fieldsCheck());
        }
    };

    private void loginUser() {
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.loginUser(new LoginRequest(email, password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.isSuccess()) {
                        AuthenticationManager authenticationManager = new AuthenticationManager(LoginActivity.this);
                        authenticationManager.saveTokens(email, loginResponse.getToken(), loginResponse.getRefreshToken());
                        System.out.println(loginResponse.getToken());
                        System.out.println(loginResponse.getRefreshToken());

                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finishAffinity();
                    } else {
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        LoginResponse errorResponse = new Gson().fromJson(response.errorBody().string(), LoginResponse.class);
                        Toast.makeText(LoginActivity.this, errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Une erreur est survenue.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("LoginActivity", "loginUser onFailure: " + t.getMessage());
            }
        });
    }
}