package fr.stvenchg.bleatz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.adapter.BurgerIngredientsAdapter;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.burger.DetailsBurgerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BurgerActivity extends AppCompatActivity {

    private DetailsBurgerResponse detailsBurgerResponse;
    private int idBurger;

    private RecyclerView recyclerView;

    private BurgerIngredientsAdapter ingredientAdapter;

    private List<DetailsBurgerResponse.Burger.Ingredient> listeIngredients;

    private DetailsBurgerResponse.Burger burger;
    private TextView burgerNameView, burgerPriceView, burgerDescriptionView;
    private ImageView burgerImageView;
    private Button ajouterAuPanier;

    private TextView toolbarTitle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burger);

        Intent intent = getIntent();
        idBurger = intent.getIntExtra("burger_id", 0);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.content_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        detailsBurgerResponse = new DetailsBurgerResponse();

        listeIngredients = new ArrayList<>();
        ingredientAdapter = new BurgerIngredientsAdapter(listeIngredients, this);
        fetchContent();

        recyclerView.setAdapter(ingredientAdapter);

        ajouterAuPanier = findViewById(R.id.btn_add_to_cart);

        ajouterAuPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(BurgerActivity.this, BoissonActivity.class);
                intent.putExtra("burger_id", burger.getIdBurger());
                BurgerActivity.this.startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


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

    private void fetchContent() {

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            retrofit2.Call<DetailsBurgerResponse> call = apiInterface.getBurgersDetails(idBurger);

            call.enqueue(new Callback<DetailsBurgerResponse>() {
                @Override
                public void onResponse(Call<DetailsBurgerResponse> call, Response<DetailsBurgerResponse> response) {
                    if (response.isSuccessful()) {
                        listeIngredients.clear();
                        detailsBurgerResponse = response.body();
                        burger = detailsBurgerResponse.getBurgers().get(0);
                        listeIngredients.addAll(detailsBurgerResponse.getBurgers().get(0).getIngredients());
                        ingredientAdapter.notifyDataSetChanged();

                        burgerNameView = findViewById(R.id.burger_name_view);
                        burgerNameView.setText(burger.getNom());

                        burgerPriceView = findViewById(R.id.burger_price_view);
                        burgerPriceView.setText(String.valueOf(burger.getPrix()) + "0 €");

                        burgerDescriptionView = findViewById(R.id.burger_description_view);
                        burgerDescriptionView.setText(burger.getDescription());

                        burgerImageView = findViewById(R.id.burger_image_view);

                        toolbarTitle = findViewById(R.id.toolbar_title);
                        toolbarTitle.setText(burger.getNom());


                        Glide.with(BurgerActivity.this)
                                .load("https://api.stevenching.fr/bleatz/img/burger/" + burger.getImage())
                                .override(900,900)
                                .into(burgerImageView);
                    }
                }

                @Override
                public void onFailure(Call<DetailsBurgerResponse> call, Throwable t) {

                }

            });
    }

}