package fr.stvenchg.bleatz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.account.AccountResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskEmailConfirmActivity extends AppCompatActivity {
    private static final long CHECK_INTERVAL = 5000; // 5 secondes
    private Handler mHandler;
    private AuthenticationManager authenticationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askemailconfirm);

        authenticationManager = new AuthenticationManager(this);
        mHandler = new Handler();
        startCheckingEmailVerification();
    }

    private void startCheckingEmailVerification() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkEmailVerificationStatus();
                mHandler.postDelayed(this, CHECK_INTERVAL);
            }
        }, CHECK_INTERVAL);
    }

    private void checkEmailVerificationStatus() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AccountResponse> call = apiInterface.getAccount("Bearer " + authenticationManager.getAccessToken());

        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    AccountResponse accountResponse = response.body();
                    if (accountResponse != null && accountResponse.isSuccess() && accountResponse.isEmailVerified()) {
                        // Email vérifié, arrêtez de vérifier et redirigez vers une autre activité
                        mHandler.removeCallbacksAndMessages(null);

                        Intent intent = new Intent(AskEmailConfirmActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        Toast.makeText(AskEmailConfirmActivity.this, "Adresse e-mail vérifiée.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                // Gérer l'échec de la requête
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}