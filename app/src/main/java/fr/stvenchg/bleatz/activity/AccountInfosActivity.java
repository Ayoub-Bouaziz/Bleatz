package fr.stvenchg.bleatz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.AuthenticationManager;

public class AccountInfosActivity extends AppCompatActivity {

    private EditText firstnameEditText;
    private EditText lastnameEditText;
    private EditText phoneEditText;
    private EditText addressEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    private Button saveChangesButton;
    private TextView deleteAccountTextView;

    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountinfos);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        AuthenticationManager authenticationManager = new AuthenticationManager(this);

        firstnameEditText = findViewById(R.id.accountinfos_edittext_firstname);
        lastnameEditText = findViewById(R.id.accountinfos_edittext_lastname);
        phoneEditText = findViewById(R.id.accountinfos_edittext_phone);
        addressEditText = findViewById(R.id.accountinfos_edittext_address);
        emailEditText = findViewById(R.id.accountinfos_edittext_email);
        passwordEditText = findViewById(R.id.accountinfos_edittext_password);

        saveChangesButton = findViewById(R.id.accountinfos_button_savechanges);
        saveChangesButton.setEnabled(false);

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
        addressEditText.setInputType(InputType.TYPE_NULL);
        emailEditText.setText(email);

        addressEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddress = new Intent(AccountInfosActivity.this, AddAddressActivity.class);
                startActivityForResult(intentAddress, REQUEST_CODE);

                String address = intentAddress.getStringExtra("address");
                addressEditText.setText(address);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String newAddress = data.getStringExtra("address");
            if (newAddress != null) {
                addressEditText.setText(newAddress);
            }
        }
    }
}
