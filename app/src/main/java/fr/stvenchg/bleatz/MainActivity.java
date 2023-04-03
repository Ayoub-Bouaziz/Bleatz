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
        replaceFragment(new HomeFragment(), R.id.nav_item_home);

        binding.navItemHome.setOnClickListener(v -> replaceFragment(new HomeFragment(), R.id.nav_item_home));
        binding.navItemBurgermenu.setOnClickListener(v -> replaceFragment(new BurgerMenuFragment(), R.id.nav_item_burgermenu));
        binding.navItemLocation.setOnClickListener(v -> replaceFragment(new RestaurantLocationFragment(), R.id.nav_item_location));
        binding.navItemAccount.setOnClickListener(v -> replaceFragment(new AccountFragment(), R.id.nav_item_account));

        authenticationManager = new AuthenticationManager(this);
        checkEmailVerified();
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

