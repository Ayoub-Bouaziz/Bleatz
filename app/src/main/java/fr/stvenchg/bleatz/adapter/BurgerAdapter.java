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

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.activity.AuthActivity;
import fr.stvenchg.bleatz.activity.BurgerActivity;
import fr.stvenchg.bleatz.api.burger.BurgerResponse;

public class BurgerAdapter extends RecyclerView.Adapter<BurgerAdapter.BurgerViewHolder> {

    private List<BurgerResponse.Burger> burgers;
    private static Context context;

    public BurgerAdapter(List<BurgerResponse.Burger> burgers, Context context) {
        this.context = context;
        this.burgers = burgers;
    }


    public List<BurgerResponse.Burger> getBurgers() {
        return burgers;
    }

    public void setBurgers(List<BurgerResponse.Burger> burgers) {
        this.burgers = burgers;
    }

    @NonNull
    @Override
    public BurgerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_burger, parent, false);
        return new BurgerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull BurgerViewHolder holder, int position) {
        BurgerResponse.Burger burger = burgers.get(position);
        holder.bind(burger);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BurgerActivity.class);
                intent.putExtra("burger_id", burger.getIdBurger());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.burgers.size();
    }


    public static class BurgerViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView nom;
        public TextView description;
        public TextView prix;

        public BurgerViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.burger_image);
            nom = itemView.findViewById(R.id.burger_nom);
            // description = itemView.findViewById(R.id.burger_description);
            prix = itemView.findViewById(R.id.burger_prix);
        }

        public void bind(BurgerResponse.Burger burger) {

            nom.setText(burger.getNom());
            // description.setText(burger.getDescription());
            prix.setText(String.valueOf(burger.getPrix()) + "0 €");

            Glide.with(context)
                    .load("https://api.stevenching.fr/bleatz/img/burger/" + burger.getImage())
                    .into(image);
        }
    }
}