package fr.stvenchg.bleatz.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.adapter.MenuAdapter;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.burger.BurgerResponse;
import fr.stvenchg.bleatz.api.menu.MenuResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private ListView listeMenus;
    private List<MenuResponse> menus = new ArrayList<>();
    private List<BurgerResponse> burgers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialisation de la liste de menus
        listeMenus = findViewById(R.id.list_articles);

        // Récupération du token d'authentification depuis les préférences partagées
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String accessToken = preferences.getString("accessToken", "");

        // Récupération de la liste de menus et de burgers depuis l'API
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<MenuResponse>> callMenus = apiInterface.getMenus("Bearer " + accessToken);
        callMenus.enqueue(new Callback<List<MenuResponse>>() {
            @Override
            public void onResponse(Call<List<MenuResponse>> call, Response<List<MenuResponse>> response) {
                if (response.isSuccessful()) {
                    menus = response.body();

                    // Récupération de la liste de burgers
                    Call<List<BurgerResponse>> callBurgers = apiInterface.getBurgers("Bearer " + accessToken);
                    callBurgers.enqueue(new Callback<List<BurgerResponse>>() {
                        @Override
                        public void onResponse(Call<List<BurgerResponse>> call, Response<List<BurgerResponse>> response) {
                            if (response.isSuccessful()) {
                                burgers = response.body();

                                // Association de chaque burger au menu correspondant
                                for (MenuResponse menu : menus) {
                                    for (BurgerResponse burger : burgers) {


                                            break;

                                    }
                                }

                                // Création de l'adaptateur pour la liste de menus
                                MenuAdapter adapter = new MenuAdapter(CartActivity.this, menus);
                                listeMenus.setAdapter(adapter);
                            } else {
                                Toast.makeText(CartActivity.this, "Erreur : " + response.message(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<List<BurgerResponse>> call, Throwable t) {
                            Toast.makeText(CartActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(CartActivity.this, "Erreur : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MenuResponse>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
