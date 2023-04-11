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
        Call<List<CartResponse>> callCart = apiInterface.getCart("Bearer " + accessToken);
        System.out.println("----------------------4-------------------------------");
        callCart.enqueue(new Callback<List<CartResponse>>() {
            @Override
            public void onResponse(Call<List<CartResponse>> call, Response<List<CartResponse>> response) {
                System.out.println("-----------------5------------------------------------");
                System.out.println(response);
                if (response.isSuccessful()) {
                    System.out.println("----------------6-------------------------------------");
                    List<CartResponse> cartList = response.body();
                    List<CartResponse.MenuItem> menuItems = new ArrayList<>();
                    for (CartResponse cart : cartList) {
                        for (CartResponse.MenuContent menuContent : cart.getContent()) {
                            for (CartResponse.MenuItem menuItem : menuContent.getContent()) {
                                menuItems.add(menuItem);
                            }
                        }
                    }

                    // Affichage des menus du panier dans la liste
                    CartListAdapter adapter = new CartListAdapter(menuItems);
                    listViewCart.setAdapter(adapter);

                    // Calcul et affichage du prix total du panier
                    double total = 0;
                    for (CartResponse cart : cartList) {
                        total += cart.getTotal_price();
                    }
                    totalPrice.setText("Total : " + total + "€");

                } else {
                    Toast.makeText(CartActivity.this, "Impossible de récupérer le panier", Toast.LENGTH_SHORT).show();
                    System.out.println("----------------erreur-------------------------------------");
                }

            }
            @Override
            public void onFailure(Call<List<CartResponse>> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("----------------erreur2-------------------------------------");
            }
        });
/*
        // Configuration du bouton de commande
        btnCommander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérification de l'authentification de l'utilisateur
                if (!authenticationManager.isUserAuthenticated()) {
                    Toast.makeText(CartActivity.this, "Vous devez vous connecter pour commander", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CartActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }

                // Récupération des menus dans le panier
                List<Menu> menus = new ArrayList<>();
                for (int i = 0; i < listViewCart.getCount(); i++) {
                    View view = listViewCart.getChildAt(i);

                    CartResponse cart = (CartResponse) listViewCart.getItemAtPosition(i);
                    Menu menu = cart.getM;

                    menus.add(menu);
                }

                // Envoi de la commande à l'API
                Call<OrderResponse> callOrder = apiInterface.createOrder("Bearer " + accessToken, new OrderRequest(menus));
                callOrder.enqueue(new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CartActivity.this, "Commande envoyée avec succès", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CartActivity.this, OrderDetailsActivity.class);
                            intent.putExtra("orderId", response.body().getId());
                            startActivity(intent

            }
        });
*/
    }
}

