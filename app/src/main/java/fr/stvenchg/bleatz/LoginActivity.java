package fr.stvenchg.bleatz;

import android.os.Bundle;
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
    }
}
