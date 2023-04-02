package fr.stvenchg.bleatz;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

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
}