package fr.stvenchg.bleatz.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.stvenchg.bleatz.R;
import fr.stvenchg.bleatz.api.ingredient.IngredientResponse;

public class CheckIngredientsAdapter extends RecyclerView.Adapter<CheckIngredientsAdapter.ViewHolder>{

    private List<IngredientResponse.Ingredient> ingredients;
    private List<Integer> selectedIds = new ArrayList<>();

    public CheckIngredientsAdapter(List<IngredientResponse.Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_item_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IngredientResponse.Ingredient ingredient = ingredients.get(position);
        holder.ingredientName.setText(ingredient.getName());

        holder.ingredientCheckbox.setOnCheckedChangeListener(null);
        holder.ingredientCheckbox.setChecked(selectedIds.contains(ingredient.getId()));

        holder.ingredientCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedIds.add(ingredient.getId());
            } else {
                selectedIds.remove(Integer.valueOf(ingredient.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public List<Integer> getSelectedIds() {
        return selectedIds;
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
