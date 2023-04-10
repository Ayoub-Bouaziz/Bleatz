package fr.stvenchg.bleatz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.stvenchg.bleatz.R;

public class AccountInfosActivity extends AppCompatActivity {

    private EditText firstnameEditText;
    private EditText lastnameEditText;
    private EditText phoneEditText;
    private EditText addressEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    private Button saveChangesButton;
    private TextView deleteAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountinfos);

        firstnameEditText = findViewById(R.id.accountinfos_edittext_firstname);
        lastnameEditText = findViewById(R.id.accountinfos_edittext_lastname);
        phoneEditText = findViewById(R.id.accountinfos_edittext_phone);
        addressEditText = findViewById(R.id.accountinfos_edittext_address);
        emailEditText = findViewById(R.id.accountinfos_edittext_email);
        passwordEditText = findViewById(R.id.accountinfos_edittext_password);

        saveChangesButton = findViewById(R.id.accountinfos_button_savechanges);
        deleteAccountTextView = findViewById(R.id.accountinfos_textview_deleteaccount);

        Intent intent = getIntent();
        String idUser = intent.getStringExtra("idUser");
        String firstname = intent.getStringExtra("firstname");
        String lastname = intent.getStringExtra("lastname");
        String phone = intent.getStringExtra("phone");
        String address = intent.getStringExtra("address");
        String email = intent.getStringExtra("email");

        firstnameEditText.setText(firstname);
        lastnameEditText.setText(lastname);
        phoneEditText.setText(phone);
        addressEditText.setText(address);
        emailEditText.setText(email);
    }
}
