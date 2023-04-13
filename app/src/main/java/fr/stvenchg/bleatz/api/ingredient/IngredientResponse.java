package fr.stvenchg.bleatz.api.ingredient;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class IngredientResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("ingredients")
    private List<Ingredient> ingredients;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public static class Ingredient {
        @SerializedName("idIngredient")
        private int id;

        @SerializedName("nom")
        private String name;

        @SerializedName("prix")
        private double price;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }
}
