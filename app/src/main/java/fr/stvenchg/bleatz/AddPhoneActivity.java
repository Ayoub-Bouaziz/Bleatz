package fr.stvenchg.bleatz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.phone.send.PhoneSendRequest;
import fr.stvenchg.bleatz.api.phone.send.PhoneSendResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPhoneActivity extends AppCompatActivity {

    private AuthenticationManager authenticationManager;
    private TextInputEditText phoneEditText;

    private Button sendCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addphone);

        phoneEditText = findViewById(R.id.addphone_edittext_phone);
        sendCodeButton = findViewById(R.id.addphone_button_sendcode);

        // Désactiver le bouton
        sendCodeButton.setEnabled(false);

        // Vérification des champs du formulaire
        phoneEditText.addTextChangedListener(textWatcher);
        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phoneEditText.getText().toString();

                // Envoyer le code de vérification
                sendPhoneCode("33" + phoneNumber);
            }
        });
    }

    private boolean isValidPhone(String phone_number) {
        boolean valid = false;

        if (phone_number == null) {
            valid = false;
        }

        if (phone_number.length() > 8 && phone_number.length() <= 10) {
            valid = true;
        }

        return valid;
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
            sendCodeButton.setEnabled(isValidPhone(phoneEditText.getText().toString()));
        }
    };

    private void sendPhoneCode(String phoneNumber) {
        AuthenticationManager authenticationManager = new AuthenticationManager(this);
        String accessToken = authenticationManager.getAccessToken();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        PhoneSendRequest phoneSendRequest = new PhoneSendRequest(phoneNumber);
        Call<PhoneSendResponse> call = apiService.sendPhoneCode("Bearer " + accessToken, phoneSendRequest);
        call.enqueue(new Callback<PhoneSendResponse>() {
            @Override
            public void onResponse(Call<PhoneSendResponse> call, Response<PhoneSendResponse> response) {
                if (response.isSuccessful()) {
                    PhoneSendResponse phoneSendResponse = response.body();
                    if (phoneSendResponse.isSuccess()) {
                        Intent intent = new Intent(AddPhoneActivity.this, MainActivity.class);
                        intent.putExtra("phone_number", phoneNumber);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AddPhoneActivity.this, phoneSendResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddPhoneActivity.this, "Une erreur est survenue.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PhoneSendResponse> call, Throwable t) {
                Toast.makeText(AddPhoneActivity.this, "Impossible de se connecter au serveur. Veuillez réessayer.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
