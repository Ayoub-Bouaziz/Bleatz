package fr.stvenchg.bleatz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.stvenchg.bleatz.api.AuthenticationManager;

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
        String accessToken = authenticationManager.getAccessToken();
        String refreshToken = authenticationManager.getRefreshToken();

        if (accessToken != null && refreshToken != null) {
            // Si les tokens existent, démarrez MainActivity
            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Terminez AuthActivity pour éviter de revenir en arrière avec le bouton Retour
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

}
