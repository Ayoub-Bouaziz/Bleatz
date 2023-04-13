package fr.stvenchg.bleatz.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.ingredient.IngredientResponse;

public class CheckIngredientsAdapter extends RecyclerView.Adapter<CheckIngredientsAdapter.ViewHolder>{

    private List<IngredientResponse.Ingredient> ingredients;

    public CheckIngredientsAdapter(List<IngredientResponse.Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_item_ingredient, parent, false);
        return new ViewHolder(view);
    }


    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientResponse.Ingredient ingredient = ingredients.get(position);
        holder.ingredientName.setText(ingredient.getName());
    }


    public int getItemCount() {
        return ingredients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox ingredientCheckbox;
        private TextView ingredientName;

        public ViewHolder(View itemView) {
            super(itemView);
            ingredientCheckbox = itemView.findViewById(R.id.ingredient_checkbox);
            ingredientName = itemView.findViewById(R.id.ingredient_name);
        }
    }

}
