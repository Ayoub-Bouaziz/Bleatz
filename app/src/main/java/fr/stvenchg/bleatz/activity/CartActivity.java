package fr.stvenchg.bleatz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialisation de la liste de menus dans le panier
        listViewCart = findViewById(R.id.list_articles);
        btnCommander = findViewById(R.id.btn_commander);
        totalPrice = findViewById(R.id.txt_total);
        fetchCart();

        // Répétition de l'appel à fetchCart toutes les secondes
        runnable = new Runnable() {
            public void run() {
                fetchCart();
                handler.postDelayed(this, 5000);
            }
        };
        handler.postDelayed(runnable, 5000);

        btnCommander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                envoiCommande();
            }
             });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Arrêt de la répétition de l'appel à fetchCart lorsque l'activité est détruite
        handler.removeCallbacks(runnable);
    }

    public void envoiCommande(){
/*
        // Récupération du token d'authentification depuis les préférences partagées
        AuthenticationManager authenticationManager = new AuthenticationManager(this);
        String accessToken = authenticationManager.getAccessToken();

        // Récupération du panier depuis l'API
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CommandeResponse> callCart = apiInterface.getCart("Bearer " + accessToken);
        callCart.enqueue(new Callback<CommandeResponse>() {
            @Override
            public void onResponse(Call<CommandeResponse> call, Response<CommandeResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CartActivity.this, "Votre commande est envoyé à la cuisine ", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CartActivity.this, "Impossible de récupérer le panier", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CommandeResponse> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                System.out.println("----------------erreur2-------------------------------------");
            }
        });
        }
*/
    }

    private void fetchCart(){
        // Récupération du token d'authentification depuis les préférences partagées
        AuthenticationManager authenticationManager = new AuthenticationManager(this);
        String accessToken = authenticationManager.getAccessToken();

        // Récupération du panier depuis l'API
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CartResponse> callCart = apiInterface.getCart("Bearer " + accessToken);
        callCart.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful()) {
                    CartResponse cart = response.body();
                    List<CartResponse.MenuContent> menuContents = new ArrayList<>() ;

                    for (CartResponse.MenuContent menuContent : cart.getContent()) {
                        menuContents.add(menuContent);
                    }

                    // Affichage des menus du panier dans la liste
                    CartListAdapter adapter = new CartListAdapter(menuContents,CartActivity.this);
                    adapter.notifyDataSetChanged();
                    listViewCart.setAdapter(adapter);

                    // Calcul et affichage du prix total du panier

                    double total = cart.getTotal_price();
                    NumberFormat formatter = new DecimalFormat("#0.00");
                    String formattedPrice = formatter.format(total);
                    totalPrice.setText("Total : " + formattedPrice + " €");

                } else {
                    Toast.makeText(CartActivity.this, "Impossible de récupérer le panier", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                System.out.println("----------------erreur2-------------------------------------");
            }
        });
    }
}

