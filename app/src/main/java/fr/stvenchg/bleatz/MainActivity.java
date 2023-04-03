package fr.stvenchg.bleatz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.atomic.AtomicBoolean;

import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.account.AccountResponse;
import fr.stvenchg.bleatz.databinding.ActivityMainBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private AuthenticationManager authenticationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.navigation.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.nav_item_home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.nav_item_burgermenu:
                    replaceFragment(new BurgerMenuFragment());
                    break;
                case R.id.nav_item_location:
                    replaceFragment(new RestaurantLocationFragment());
                    break;
                case R.id.nav_item_account:
                    replaceFragment(new AccountFragment());
                    break;
            }

            return true;
        });

        authenticationManager = new AuthenticationManager(this);
        checkEmailVerified();
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_framelayout, fragment);
        fragmentTransaction.commit();
    }

    private void checkEmailVerified() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AccountResponse> call = apiInterface.getAccount("Bearer " + authenticationManager.getAccessToken());

        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    AccountResponse accountResponse = response.body();
                    if (accountResponse != null && accountResponse.isSuccess()) {
                        if (!accountResponse.isEmailVerified()) {
                            redirectToAskEmailConfirmActivity();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                // Gérer l'échec de la requête
            }
        });
    }

    private void redirectToAskEmailConfirmActivity() {
        Intent intent = new Intent(MainActivity.this, AskEmailConfirmActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        finish();
    }
}

