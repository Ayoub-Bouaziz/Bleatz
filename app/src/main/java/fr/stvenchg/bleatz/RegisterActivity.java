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
import fr.stvenchg.bleatz.api.login.LoginCallback;
import fr.stvenchg.bleatz.api.login.LoginRequest;
import fr.stvenchg.bleatz.api.login.LoginResponse;
import fr.stvenchg.bleatz.api.register.RegistrationRequest;
import fr.stvenchg.bleatz.api.register.RegistrationResponse;
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

        // Vérification des champs du formulaire
        mFirstnameEditText.addTextChangedListener(textWatcher);
        mLastnameEditText.addTextChangedListener(textWatcher);
        mEmailEditText.addTextChangedListener(textWatcher);
        mPasswordEditText.addTextChangedListener(textWatcher);
        mConfirmPasswordEditText.addTextChangedListener(textWatcher);

        // Désactiver le bouton d'inscription
        mRegisterSendButton.setEnabled(false);

        mRegisterSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
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

        return !TextUtils.isEmpty(mFirstnameEditText.getText().toString()) &&
                !TextUtils.isEmpty(mLastnameEditText.getText().toString()) &&
                isValidEmail(email) &&
                !TextUtils.isEmpty(password) &&
                password.length() > 6 &&
                !TextUtils.isEmpty(mConfirmPasswordEditText.getText().toString()) &&
                password.equals(mConfirmPasswordEditText.getText().toString());
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
            mRegisterSendButton.setEnabled(fieldsCheck());
        }
    };

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

                            // Identification de l'utilisateur
                            loginUser(email, password, new LoginCallback() {
                                @Override
                                public void onLoginSuccess() {
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    finishAffinity();
                                }

                                @Override
                                public void onLoginFailure(String message) {
                                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                            });
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
                Toast.makeText(RegisterActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("RegisterActivity", "onFailure: " + t.getMessage());
            }
        });
    }

    private void loginUser(String email, String password, LoginCallback callback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> call = apiInterface.loginUser(new LoginRequest(email, password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.isSuccess()) {
                        AuthenticationManager authenticationManager = new AuthenticationManager(RegisterActivity.this);
                        authenticationManager.saveTokens(email, loginResponse.getToken(), loginResponse.getRefreshToken());
                        System.out.println(loginResponse.getToken());
                        System.out.println(loginResponse.getRefreshToken());
                        callback.onLoginSuccess();
                    } else {
                        callback.onLoginFailure(loginResponse.getMessage());
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Une erreur est survenue.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onLoginFailure(t.getMessage());
                Log.e("RegisterActivity", "loginUser onFailure: " + t.getMessage());
            }
        });
    }
}