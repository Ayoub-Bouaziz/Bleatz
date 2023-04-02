package fr.stvenchg.bleatz;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

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
    }
}
