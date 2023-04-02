package fr.stvenchg.bleatz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.refreshToken.RefreshTokenRequest;
import fr.stvenchg.bleatz.api.refreshToken.RefreshTokenResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {

    private Button mLoginButton;
    private Button mRegisterButton;
    private TextView mLaterTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        AuthenticationManager authenticationManager = new AuthenticationManager(this);

        // Vérifier si des tokens sont déjà enregistrés et qu'ils sont valides
        String email = authenticationManager.getEmail();
        String refreshToken = authenticationManager.getRefreshToken();
        System.out.println(email);
        System.out.println(refreshToken);

        if (refreshToken != null && email != null) {
            refreshAccessToken(email, refreshToken);
        }

        mLoginButton = findViewById(R.id.auth_button_login);
        mRegisterButton = findViewById(R.id.auth_button_register);
        mLaterTextView = findViewById(R.id.auth_textview_later);

        // Démarrage de l'activité de connexion
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        // Démarrage de l'activité d'inscription
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void refreshAccessToken(String email, String refreshToken) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RefreshTokenResponse> call = apiInterface.refreshUserToken(new RefreshTokenRequest(email, refreshToken));

        call.enqueue(new Callback<RefreshTokenResponse>() {
            @Override
            public void onResponse(Call<RefreshTokenResponse> call, Response<RefreshTokenResponse> response) {
                if (response.isSuccessful()) {
                    RefreshTokenResponse refreshTokenResponse = response.body();
                    if (refreshTokenResponse != null && refreshTokenResponse.isSuccess()) {
                        AuthenticationManager authenticationManager = new AuthenticationManager(AuthActivity.this);
                        authenticationManager.saveTokens(authenticationManager.getEmail(), refreshTokenResponse.getToken(), authenticationManager.getRefreshToken());

                        System.out.println(authenticationManager.getEmail());
                        System.out.println(authenticationManager.getAccessToken());
                        System.out.println(authenticationManager.getRefreshToken());

                        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Terminez AuthActivity pour éviter de revenir en arrière avec le bouton Retour
                    }
                    else {
                        AuthenticationManager authenticationManager = new AuthenticationManager(AuthActivity.this);
                        authenticationManager.clearTokens();

                        Toast.makeText(AuthActivity.this, refreshTokenResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RefreshTokenResponse> call, Throwable t) {
                Log.e("AuthActivity", "refreshAccessToken onFailure: " + t.getMessage());
            }
        });
    }
}
