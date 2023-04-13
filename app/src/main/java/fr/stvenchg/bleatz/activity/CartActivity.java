package fr.stvenchg.bleatz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import fr.stvenchg.bleatz.api.complete.CompleteResponse;
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

    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Initialisation de la liste de menus dans le panier
        listViewCart = findViewById(R.id.list_articles);
        btnCommander = findViewById(R.id.btn_commander);
        totalPrice = findViewById(R.id.txt_total);
        toolbarTitle = findViewById(R.id.toolbar_title);
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

        // Récupération du token d'authentification depuis les préférences partagées
        AuthenticationManager authenticationManager = new AuthenticationManager(this);
        String accessToken = authenticationManager.getAccessToken();

        // Récupération du panier depuis l'API
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<CompleteResponse> callCart = apiInterface.getComplete("Bearer " + accessToken);
        callCart.enqueue(new Callback<CompleteResponse>() {
            @Override
            public void onResponse(Call<CompleteResponse> call, Response<CompleteResponse> response) {
                if (response.isSuccessful()) {
                    CompleteResponse complete = response.body();
                    if (complete.isSuccess()) {
                        Toast.makeText(CartActivity.this, "Commande passée, merci !", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(CartActivity.this, "Votre panier est vide.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CartActivity.this, "Une erreur est survenue lors du passage de la commande.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CompleteResponse> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                System.out.println("----------------erreur2-------------------------------------");
            }
        });
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
                    Toast.makeText(CartActivity.this, "Erreur lors de la récupération du panier.", Toast.LENGTH_SHORT).show();
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
}

