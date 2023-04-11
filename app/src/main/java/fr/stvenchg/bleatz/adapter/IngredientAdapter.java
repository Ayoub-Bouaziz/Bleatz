package fr.stvenchg.bleatz.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.kitchen.OrderContentResponse;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private List<OrderContentResponse.CommandeContent.Burger.Ingredient> ingredientsList;

    public IngredientAdapter(List<OrderContentResponse.CommandeContent.Burger.Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderContentResponse.CommandeContent.Burger.Ingredient ingredient = ingredientsList.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public void setIngredients(List<OrderContentResponse.CommandeContent.Burger.Ingredient> ingredients) {
        this.ingredientsList = ingredients;
        this.notifyDataSetChanged();
    }

    public List<OrderContentResponse.CommandeContent.Burger.Ingredient> getIngredients() {
        return this.ingredientsList;
    }

    public void addIngredients(OrderContentResponse.CommandeContent.Burger.Ingredient ingredient) {
        this.ingredientsList.add(ingredient);
        this.notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ingredientNameTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientNameTextView = itemView.findViewById(R.id.ingredient_name_text_view);
        }

        public void bind(OrderContentResponse.CommandeContent.Burger.Ingredient ingredient) {
            ingredientNameTextView.setText("x1 " + ingredient.getNom());
        }

    }
}

