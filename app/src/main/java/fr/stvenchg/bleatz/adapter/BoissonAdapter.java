package fr.stvenchg.bleatz.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.boisson.BoissonResponse;
import fr.stvenchg.bleatz.api.panier.CreateMenuResponse;

public class BoissonAdapter extends RecyclerView.Adapter<BoissonAdapter.BoissonViewHolder>  {

    private List<BoissonResponse.Boisson> boissons;
    private int idBurger ;
    private static Context context;

    public BoissonAdapter(List<BoissonResponse.Boisson> boissons,int idBurger, Context context) {
        this.context = context;
        this.boissons = boissons;
        this.idBurger =idBurger ;
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
                /*
                Intent intent = new Intent(context, BurgerActivity.class);
                intent.putExtra("boisson_id", boisson.getIdBoisson());
                context.startActivity(intent);

                 */

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
