package fr.stvenchg.bleatz.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.activity.CartActivity;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.boisson.BoissonResponse;
import fr.stvenchg.bleatz.api.panier.AddToCartResponse;
import fr.stvenchg.bleatz.api.panier.CreateMenuResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoissonAdapter extends RecyclerView.Adapter<BoissonAdapter.BoissonViewHolder>  {

    private List<BoissonResponse.Boisson> boissons;
    private int idBurger ;
    private static Context context;

    private Activity activity;

    public BoissonAdapter(List<BoissonResponse.Boisson> boissons,int idBurger, Context context, Activity activity) {
        this.context = context;
        this.boissons = boissons;
        this.idBurger = idBurger;
        this.activity = activity;
    }


    public List<BoissonResponse.Boisson> getBoisson() {
        return boissons;
    }

    public void setBurgers(List<BoissonResponse.Boisson> boissons) {
        this.boissons = boissons;
    }

    @NonNull
    @Override
    public BoissonAdapter.BoissonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_boisson, parent, false);
        return new BoissonAdapter.BoissonViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BoissonAdapter.BoissonViewHolder holder, int position) {
        BoissonResponse.Boisson boisson = boissons.get(position);
        holder.bind(boisson);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AuthenticationManager authenticationManager = new AuthenticationManager(context);
                String accessToken = authenticationManager.getAccessToken();

                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                retrofit2.Call<CreateMenuResponse> call = apiInterface.createMenu("Bearer " + accessToken,idBurger, boisson.getIdBoisson());

                call.enqueue(new Callback<CreateMenuResponse>() {
                    @Override
                    public void onResponse(Call<CreateMenuResponse> call, Response<CreateMenuResponse> response) {
                        if (response.isSuccessful()) {

                            CreateMenuResponse menuResponse = new CreateMenuResponse();
                            menuResponse = response.body();
                            AddCart(menuResponse.getIdMenu());

                        } else {
                            Toast.makeText(context, "Impossible de récupérer le menu", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CreateMenuResponse> call, Throwable t) {
                        System.out.println("------------------ee-----------");
                    }


                });





            }
        });

    }

    private void AddCart(int idMenu){
        AuthenticationManager authenticationManager = new AuthenticationManager(context);
        String accessToken = authenticationManager.getAccessToken();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        retrofit2.Call<AddToCartResponse> call = apiInterface.addToCart("Bearer " + accessToken, idMenu);

        call.enqueue(new Callback<AddToCartResponse>() {
            @Override
            public void onResponse(Call<AddToCartResponse> call, Response<AddToCartResponse> response) {
                if (response.isSuccessful()) {
                    AddToCartResponse addToCartResponse = response.body();

                    Toast.makeText(context, "Menu ajouté au panier.", Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
            }

            @Override
            public void onFailure(Call<AddToCartResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return this.boissons.size();
    }


    public static class BoissonViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView nom;
        public TextView prix;

        public BoissonViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.boisson_image);
            nom = itemView.findViewById(R.id.boisson_nom);
            prix = itemView.findViewById(R.id.boisson_prix);
        }

        public void bind(BoissonResponse.Boisson boisson) {

            nom.setText(boisson.getNom());
            prix.setText(String.valueOf(boisson.getPrix()) + "0 €");

            Glide.with(context)
                    .load("https://api.stevenching.fr/bleatz/img/boisson/" + boisson.getImage())
                    .into(image);
        }
    }

}
