package fr.stvenchg.bleatz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.account.AccountResponse;
import fr.stvenchg.bleatz.api.set.SetRequest;
import fr.stvenchg.bleatz.api.set.SetResponse;
import fr.stvenchg.bleatz.fragment.HomeFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        addressEditText.setInputType(InputType.TYPE_NULL);
        addressEditText.setText(address);
        addressEditText.setInputType(InputType.TYPE_NULL);
        emailEditText.setText(email);

        firstnameEditText.addTextChangedListener(editTextWatcher);
        lastnameEditText.addTextChangedListener(editTextWatcher);

        addressEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddress = new Intent(AccountInfosActivity.this, AddAddressActivity.class);
                startActivityForResult(intentAddress, REQUEST_CODE);

                String address = intentAddress.getStringExtra("address");
                addressEditText.setText(address);
            }
        });

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChanges("firstname", firstnameEditText.getText().toString());
                setChanges("lastname", lastnameEditText.getText().toString());
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

    private TextWatcher editTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            saveChangesButton.setEnabled(true);
        }
    };

    private void setChanges(String field, String value) {
        AuthenticationManager authenticationManager = new AuthenticationManager(AccountInfosActivity.this);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<SetResponse> call = apiInterface.setAddress(
                "Bearer " + authenticationManager.getAccessToken(),
                new SetRequest(field, value)
        );

        call.enqueue(new Callback<SetResponse>() {
            @Override
            public void onResponse(Call<SetResponse> call, Response<SetResponse> response) {
                if (response.isSuccessful()) {
                    SetResponse updateResponse = response.body();
                    if (updateResponse != null && updateResponse.isSuccess()) {
                        Toast.makeText(AccountInfosActivity.this, updateResponse.getMessage(), Toast.LENGTH_SHORT).show();

                        if (field.equals("firstname")) {
                            authenticationManager.setFirstname(value);
                        }
                        else if (field.equals("lastname")){
                            authenticationManager.setLastname(value);
                        }

                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<SetResponse> call, Throwable t) {

            }
        });
    }
}
