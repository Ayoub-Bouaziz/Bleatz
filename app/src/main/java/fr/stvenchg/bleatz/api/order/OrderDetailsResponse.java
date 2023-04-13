package fr.stvenchg.bleatz.api.order;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsResponse {
    @SerializedName("success")
    public boolean success;

    @SerializedName("message")
    public String message;

    @SerializedName("idCommande")
    public int idCommande;

    @SerializedName("dateCommande")
    public String dateCommande;

    @SerializedName("statut")
    public String statut;

    @SerializedName("content")
    public List<OrderContent> content;

    public static class OrderContent {
        @SerializedName("idMenu")
        public int idMenu;

        @SerializedName("menus")
        public List<Menu> menus;

        @SerializedName("burgers")
        public List<Burger> burgers;

        @SerializedName("boissons")
        public List<Boisson> boissons;

        public static class Menu {
            @SerializedName("idMenu")
            public int idMenu;

            @SerializedName("prix")
            public float prix;

            @SerializedName("idBoisson")
            public int idBoisson;

            @SerializedName("idBurger")
            public int idBurger;
        }

        public static class Burger {
            @SerializedName("idBurger")
            public int idBurger;

            @SerializedName("nom")
            public String nom;

            @SerializedName("prix")
            public float prix;

            @SerializedName("description")
            public String description;

            @SerializedName("image")
            public String image;

            @SerializedName("ingredient")
            public List<Ingredient> ingredients;

            public static class Ingredient {
                @SerializedName("idIngredient")
                public int idIngredient;

                @SerializedName("prix")
                public float prix;

                @SerializedName("nom")
                public String nom;
            }
        }

        public static class Boisson {
            @SerializedName("idBoisson")
            public int idBoisson;

            @SerializedName("nom")
            public String nom;

            @SerializedName("prix")
            public float prix;

            @SerializedName("taille")
            public String taille;

            @SerializedName("image")
            public String image;
        }
    }
}