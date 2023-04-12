package fr.stvenchg.bleatz.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.burger.DetailsBurgerResponse;

public class BurgerIngredientsAdapter extends RecyclerView.Adapter<BurgerIngredientsAdapter.ViewHolder> {
    private List<DetailsBurgerResponse.Burger.Ingredient> ingredientsList;
    private Context context;

    public BurgerIngredientsAdapter(List<DetailsBurgerResponse.Burger.Ingredient> ingredientsList, Context context) {
        this.ingredientsList = ingredientsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_burger_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailsBurgerResponse.Burger.Ingredient ingredient = ingredientsList.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public void setIngredients(List<DetailsBurgerResponse.Burger.Ingredient> ingredients) {
        this.ingredientsList = ingredients;
        this.notifyDataSetChanged();
    }

    public List<DetailsBurgerResponse.Burger.Ingredient> getIngredients() {
        return this.ingredientsList;
    }

    public void addIngredients(DetailsBurgerResponse.Burger.Ingredient ingredient) {
        this.ingredientsList.add(ingredient);
        this.notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ingredientNameTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientNameTextView = itemView.findViewById(R.id.ingredient_name_text_view);
        }

        public void bind(DetailsBurgerResponse.Burger.Ingredient ingredient) {
            ingredientNameTextView.setText(ingredient.getNom());
        }

    }
}
