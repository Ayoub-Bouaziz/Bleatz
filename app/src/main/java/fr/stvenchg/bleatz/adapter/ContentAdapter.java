package fr.stvenchg.bleatz.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.activity.OrderActivity;
import fr.stvenchg.bleatz.api.ApiClient;
import fr.stvenchg.bleatz.api.ApiInterface;
import fr.stvenchg.bleatz.api.AuthenticationManager;
import fr.stvenchg.bleatz.api.kitchen.KitchenResponse;
import fr.stvenchg.bleatz.api.kitchen.OrderContentResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentViewHolder> {

    private List<OrderContentResponse.CommandeContent> contentList;
    private static Context context;

    public ContentAdapter(List<OrderContentResponse.CommandeContent> contentList, Context context) {
        this.contentList = contentList;
        this.context = context;
    }

    @NonNull
    public ContentAdapter.ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_order, parent, false);
        return new ContentAdapter.ContentViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ContentAdapter.ContentViewHolder holder, int position) {
        OrderContentResponse.CommandeContent content = contentList.get(position);
        holder.bind(content);
    }

    public int getItemCount() {
        return contentList.size();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {

        private TextView contentIdTextView;
        private TextView boissonTextView;
        private TextView burgerTextView;
        private ImageView burgerImageView;
        private RecyclerView ingredientsRecyclerView;
        private IngredientAdapter ingredientAdapter;

        public ContentViewHolder(@NonNull View itemView) {
            super(itemView);
            contentIdTextView = itemView.findViewById(R.id.content_id_text_view);
            boissonTextView = itemView.findViewById(R.id.boisson_text_view);
            burgerTextView = itemView.findViewById(R.id.burger_text_view);
            burgerImageView = itemView.findViewById(R.id.burger_image_view);


            ingredientsRecyclerView = itemView.findViewById(R.id.ingredients_recycler_view);
            ingredientsRecyclerView.setHasFixedSize(true);
            ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(context));

            ingredientAdapter = new IngredientAdapter(new ArrayList<>());




        }

        public void bind(OrderContentResponse.CommandeContent content) {
            contentIdTextView.setText("Menu " + String.valueOf(content.getIdMenu()));
            boissonTextView.setText("Boisson: " + String.valueOf(content.getBoissons().get(0).getNom()));
            burgerTextView.setText("Burger: " + String.valueOf(content.getBurgers().get(0).getNom()));
            Glide.with(context)
                    .load("https://api.stevenching.fr/bleatz/img/burger/" + content.getBurgers().get(0).getImage())
                    .override(300,300)
                    .into(burgerImageView);


            for (int i = 0 ; i < content.getBurgers().get(0).getIngredient().size() ; i++) {
                ingredientAdapter.addIngredients(content.getBurgers().get(0).getIngredient().get(i));
            }
            ingredientsRecyclerView.setAdapter(ingredientAdapter);

            System.out.println(ingredientAdapter.getIngredients());

        }
    }


}

