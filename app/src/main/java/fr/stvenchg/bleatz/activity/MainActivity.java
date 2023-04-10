package fr.stvenchg.bleatz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import fr.stvenchg.bleatz.EmailVerificationCallback;
import fr.stvenchg.bleatz.fragment.AccountFragment;

import fr.stvenchg.bleatz.fragment.BurgerMenuFragment;
import fr.stvenchg.bleatz.fragment.HomeFragment;
import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.fragment.RestaurantLocationFragment;
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
        setContentView(R.layout.activity_main);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authenticationManager = new AuthenticationManager(this);
        fetchUserDetails();

        binding.navItemHome.setOnClickListener(v -> {
            String firstname = authenticationManager.getFirstname();
            if (firstname != null) {
                replaceFragment(HomeFragment.newInstance(firstname), R.id.nav_item_home);
            } else {
                replaceFragment(new HomeFragment(), R.id.nav_item_home);
            }
        });

        binding.navItemBurgermenu.setOnClickListener(v -> replaceFragment(new BurgerMenuFragment(), R.id.nav_item_burgermenu));
        binding.navItemLocation.setOnClickListener(v -> replaceFragment(new RestaurantLocationFragment(), R.id.nav_item_location));
        binding.navItemAccount.setOnClickListener(v -> replaceFragment(new AccountFragment(), R.id.nav_item_account));


        // Vérification de l'email et du numéro de téléphone
        if (authenticationManager.getEmail() != null && authenticationManager.getAccessToken() != null && authenticationManager.getRefreshToken() != null)  {
            checkEmailVerified(new EmailVerificationCallback() {
                @Override
                public void onEmailVerificationDone(boolean isEmailVerified) {
                    if (isEmailVerified) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                checkPhoneVerified();
                            }
                        }, 1000);
                    }
                }
            });
        }
    }
    public void openCartActivity(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    private void replaceFragment(Fragment fragment, int selectedIconId) {
        updateSelectedIcon(selectedIconId);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_framelayout, fragment);
        fragmentTransaction.commit();
    }
    private void updateSelectedIcon(int selectedIconId) {
        int selectedColor = getResources().getColor(R.color.orange_300);
        int defaultColor = getResources().getColor(R.color.grey_700);

        binding.navItemHome.setColorFilter(selectedIconId == R.id.nav_item_home ? selectedColor : defaultColor);
        binding.navItemBurgermenu.setColorFilter(selectedIconId == R.id.nav_item_burgermenu ? selectedColor : defaultColor);
        binding.navItemLocation.setColorFilter(selectedIconId == R.id.nav_item_location ? selectedColor : defaultColor);
        binding.navItemAccount.setColorFilter(selectedIconId == R.id.nav_item_account ? selectedColor : defaultColor);
    }
    private void checkEmailVerified(EmailVerificationCallback callback) {
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
                            callback.onEmailVerificationDone(false);
                        } else {
                            callback.onEmailVerificationDone(true);
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
    private void checkPhoneVerified() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AccountResponse> call = apiInterface.getAccount("Bearer " + authenticationManager.getAccessToken());
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    AccountResponse accountResponse = response.body();
                    if (accountResponse != null && accountResponse.isSuccess()) {
                        if (!accountResponse.isPhoneVerified() || accountResponse.getPhone() == null) {
                            redirectToAddPhoneActivity();
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
        finishAffinity();
    }
    private void redirectToAddPhoneActivity() {
        Intent intent = new Intent(MainActivity.this, AddPhoneActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        finishAffinity();
    }
    private void fetchUserDetails() {
        AuthenticationManager authenticationManager = new AuthenticationManager(this);
        String accessToken = authenticationManager.getAccessToken();

        if (accessToken != null) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<AccountResponse> call = apiInterface.getAccount("Bearer " + accessToken);

            call.enqueue(new Callback<AccountResponse>() {
                @Override
                public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                    if (response.isSuccessful()) {
                        AccountResponse accountResponse = response.body();
                        if (accountResponse != null && accountResponse.isSuccess()) {
                            authenticationManager.saveUserDetails(
                                    accountResponse.getUserId(),
                                    accountResponse.getFirstname(),
                                    accountResponse.getLastname(),
                                    accountResponse.getEmail(),
                                    accountResponse.getPhone(),
                                    accountResponse.getCreationDate()
                                    );

                            String firstname = accountResponse.getFirstname();
                            HomeFragment homeFragment = HomeFragment.newInstance(firstname);
                            replaceFragment(homeFragment, R.id.nav_item_home);

                            System.out.println(accountResponse.getUserId());
                            System.out.println(accountResponse.getFirstname());
                            System.out.println(accountResponse.getLastname());
                        }
                    }
                }

                @Override
                public void onFailure(Call<AccountResponse> call, Throwable t) {
                    // Gérer l'échec de la requête
                }
            });
        }
    }
}

