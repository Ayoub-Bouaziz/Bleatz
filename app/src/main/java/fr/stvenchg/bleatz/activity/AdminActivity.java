package fr.stvenchg.bleatz.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.adapter.CheckIngredientsAdapter;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.burger.CreateBurgerResponse;
import fr.stvenchg.bleatz.api.complete.CompleteResponse;
import fr.stvenchg.bleatz.api.ingredient.IngredientResponse;
import fr.stvenchg.bleatz.api.panier.CreateMenuResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {

    private EditText nomBurger ;
    private EditText descriptionBurger ;
    private EditText prixBurger ;
    private Button btn_ajouter;
    private List<IngredientResponse.Ingredient> ingredients;
    private IngredientResponse ingredientResponse;
    private CheckIngredientsAdapter checkIngredientsAdapter;
    private RecyclerView ingredientRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        this.nomBurger = findViewById(R.id.admin_edittext_nom_burger);
        this.descriptionBurger = findViewById(R.id.admin_edittext_description_burger);
        this.prixBurger = findViewById(R.id.admin_edittext_prix_burger);
        this.btn_ajouter = findViewById(R.id.button_ajouter_burger_admin);

        ingredientRecyclerView = findViewById(R.id.ingredient_recycler_view);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        ingredients = new ArrayList<>();

        checkIngredientsAdapter = new CheckIngredientsAdapter(ingredients);

        fetchIngredients();


        ingredientRecyclerView.setAdapter(checkIngredientsAdapter);





        btn_ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterburger(nomBurger.getText().toString(), descriptionBurger.getText().toString(), prixBurger.getText().toString());
            }
        });
    }

    private void ajouterburger(String nomBurger ,String descriptionBurger, String prixBurger ) {


        AuthenticationManager authenticationManager = new AuthenticationManager(AdminActivity.this);
        String accessToken = authenticationManager.getAccessToken();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        double prixBurgerD = Double.parseDouble(this.prixBurger.getText().toString());
        retrofit2.Call<CreateBurgerResponse> call = apiInterface.createBurger("Bearer " + accessToken, nomBurger,prixBurgerD, descriptionBurger);

        call.enqueue(new Callback<CreateBurgerResponse>() {
            @Override
            public void onResponse(Call<CreateBurgerResponse> call, Response<CreateBurgerResponse> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(AdminActivity.this, "Votre burger a été ajouter ", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(AdminActivity.this, "Impossible d'ajouter le burger", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateBurgerResponse> call, Throwable t) {

            }
        });
    }

    private void fetchIngredients() {


        AuthenticationManager authenticationManager = new AuthenticationManager(AdminActivity.this);
        String accessToken = authenticationManager.getAccessToken();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        retrofit2.Call<IngredientResponse> call = apiInterface.getIngredients("Bearer " + accessToken);

        call.enqueue(new Callback<IngredientResponse>() {
            @Override
            public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                ingredients.clear();
                ingredientResponse = response.body();
                ingredients.addAll(ingredientResponse.getIngredients());
                checkIngredientsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<IngredientResponse> call, Throwable t) {

            }

        });
    }


}