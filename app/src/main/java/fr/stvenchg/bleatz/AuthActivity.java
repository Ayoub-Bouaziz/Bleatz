package fr.stvenchg.bleatz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;
    private TextView laterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        loginButton = findViewById(R.id.auth_button_login);
        registerButton = findViewById(R.id.auth_button_register);
        laterButton = findViewById(R.id.auth_textview_later);

        // Démarrage de l'activité permettant à l'utilisateur de se connecter
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
