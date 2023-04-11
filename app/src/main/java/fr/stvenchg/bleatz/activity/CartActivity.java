package fr.stvenchg.bleatz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.adapter.CartListAdapter;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;

import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.CartResponseWrapper;
import fr.stvenchg.bleatz.api.panier.CartResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private ListView listViewCart;
    private Button btnCommander;
    private TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialisation de la liste de menus dans le panier
        listViewCart = findViewById(R.id.list_articles);
        btnCommander = findViewById(R.id.btn_commander);
        totalPrice = findViewById(R.id.txt_total);
        System.out.println("-----------1------------------------------------------");
        // Récupération du token d'authentification depuis les préférences partagées
        AuthenticationManager authenticationManager = new AuthenticationManager(this);
        String accessToken = authenticationManager.getAccessToken();
        System.out.println("--------------2---------------------------------------");
        // Récupération du panier depuis l'API
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        System.out.println("------------3-----------------------------------------");
        Call<CartResponseWrapper> callCart = apiInterface.getCart("Bearer " + accessToken);
        callCart.enqueue(new Callback<CartResponseWrapper>() {
            @Override
            public void onResponse(Call<CartResponseWrapper> call, Response<CartResponseWrapper> response) {
                System.out.println("-----------------5------------------------------------");
                System.out.println(response);
                if (response.isSuccessful()) {
                    System.out.println("----------------6-------------------------------------");
                    CartResponseWrapper cartWrapper = response.body();
                    List<CartResponse> cartList = cartWrapper.getContent();
                    List<CartResponse.MenuItem> menuItems = new ArrayList<>();
                    for (CartResponse cart : cartList) {
                        for (CartResponse.MenuContent menuContent : cart.getContent()) {
                            if (menuContent.getContent() != null) {
                                for (CartResponse.MenuItem menuItem : menuContent.getContent()) {
                                    menuItems.add(menuItem);
                                }
                            }
                        }
                    }

                    // Affichage des menus du panier dans la liste
                    CartListAdapter adapter = new CartListAdapter(menuItems);
                    listViewCart.setAdapter(adapter);

                    // Calcul et affichage du prix total du panier
                    double total = cartWrapper.getTotal_price();
                    totalPrice.setText("Total : " + total + "€");

                } else {
                    Toast.makeText(CartActivity.this, "Impossible de récupérer le panier", Toast.LENGTH_SHORT).show();
                    System.out.println("----------------erreur-------------------------------------");
                }

            }

            @Override
            public void onFailure(Call<CartResponseWrapper> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                System.out.println("----------------erreur2-------------------------------------");
            }

        });
    }
}

