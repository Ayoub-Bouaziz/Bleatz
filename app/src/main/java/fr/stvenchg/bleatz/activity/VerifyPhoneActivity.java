package fr.stvenchg.bleatz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.phone.verify.PhoneVerifyRequest;
import fr.stvenchg.bleatz.api.phone.verify.PhoneVerifyResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyPhoneActivity extends AppCompatActivity {
    private AuthenticationManager authenticationManager;

    private TextInputEditText number1EditText;
    private TextInputEditText number2EditText;
    private TextInputEditText number3EditText;
    private TextInputEditText number4EditText;

    private String fullCode;
    private Button verifyCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyphone);

        // Récupérer le numéro de téléphone
        String phoneNumber = getIntent().getStringExtra("phone_number");

        number1EditText = findViewById(R.id.verifyphone_edittext_n1);
        number2EditText = findViewById(R.id.verifyphone_edittext_n2);
        number3EditText = findViewById(R.id.verifyphone_edittext_n3);
        number4EditText = findViewById(R.id.verifyphone_edittext_n4);
        verifyCodeButton = findViewById(R.id.verifyphone_button_verifycode);

        number1EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    number2EditText.requestFocus();
                }
            }
        });
        number2EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    number3EditText.requestFocus();
                }

                if (s.length() == 0) {
                    number1EditText.requestFocus();
                }
            }
        });
        number3EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    number4EditText.requestFocus();
                }

                if (s.length() == 0) {
                    number2EditText.requestFocus();
                }
            }
        });
        number4EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    fullCode = number1EditText.getText().toString() + number2EditText.getText().toString() + number3EditText.getText().toString() + number4EditText.getText().toString();
                    verifyPhoneCode(phoneNumber, fullCode);
                }
                if (s.length() == 0) {
                    number3EditText.requestFocus();
                }
            }
        });

        verifyCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vérifier le code saisi
                fullCode = number1EditText.getText().toString() + number2EditText.getText().toString() + number3EditText.getText().toString() + number4EditText.getText().toString();
                verifyPhoneCode(phoneNumber, fullCode);
            }
        });
    }

    private void verifyPhoneCode(String phoneNumber, String code) {
        AuthenticationManager authenticationManager = new AuthenticationManager(this);
        PhoneVerifyRequest phoneVerifyRequest = new PhoneVerifyRequest(phoneNumber, code);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<PhoneVerifyResponse> call = apiService.verifyPhoneCode("Bearer " + authenticationManager.getAccessToken(), phoneVerifyRequest);

        call.enqueue(new Callback<PhoneVerifyResponse>() {
            @Override
            public void onResponse(Call<PhoneVerifyResponse> call, Response<PhoneVerifyResponse> response) {
                if (response.isSuccessful()) {
                    // Le code est valide, rediriger vers MainActivity
                    Intent intent = new Intent(VerifyPhoneActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Toast.makeText(VerifyPhoneActivity.this, "Le code est invalide.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PhoneVerifyResponse> call, Throwable t) {
                // Gérer l'échec de l'appel API
                Toast.makeText(VerifyPhoneActivity.this, "Erreur lors de la vérification du code.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
