package fr.stvenchg.bleatz.api.order;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OneOrderDetailsResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("idCommande")
    private int idCommande;

    @SerializedName("dateCommande")
    private String dateCommande;

    @SerializedName("statut")
    private String statut;

    @SerializedName("content")
    private List<Content> content;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public String getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public static class Content {

        @SerializedName("idMenu")
        private int idMenu;

        @SerializedName("menus")
        private List<Menu> menus;

        @SerializedName("burgers")
        private List<Burger> burgers;

        @SerializedName("boissons")
        private List<Boisson> boissons;

        public int getIdMenu() {
            return idMenu;
        }

        public void setIdMenu(int idMenu) {
            this.idMenu = idMenu;
        }

        public List<Menu> getMenus() {
            return menus;
        }

        public void setMenus(List<Menu> menus) {
            this.menus = menus;
        }

        public List<Burger> getBurgers() {
            return burgers;
        }

        public void setBurgers(List<Burger> burgers) {
            this.burgers = burgers;
        }

        public List<Boisson> getBoissons() {
            return boissons;
        }

        public void setBoissons(List<Boisson> boissons) {
            this.boissons = boissons;
        }
    }

    public static class Menu {

        @SerializedName("idMenu")
        private int idMenu;

        @SerializedName("prix")
        private double prix;

        @SerializedName("idBoisson")
        private int idBoisson;

        @SerializedName("idBurger")
        private int idBurger;

        public int getIdMenu() {
            return idMenu;
        }

        public void setIdMenu(int idMenu) {
            this.idMenu = idMenu;
        }

        public double getPrix() {
            return prix;
        }

        public void setPrix(double prix) {
            this.prix = prix;
        }

        public int getIdBoisson() {
            return idBoisson;
        }

        public void setIdBoisson(int idBoisson) {
            this.idBoisson = idBoisson;
        }

        public int getIdBurger() {
            return idBurger;
        }

        public void setIdBurger(int idBurger) {
            this.idBurger = idBurger;
        }
    }

    public static class Burger {

        @SerializedName("idBurger")
        private int idBurger;

        @SerializedName("nom")
        private String nom;

        @SerializedName("prix")
        private double prix;

        @SerializedName("description")
        private String description;

        @SerializedName("image")
        private String image;

        @SerializedName("ingredient")
        private List<Ingredient> ingredient;

        public int getIdBurger() {
            return idBurger;
        }

        public void setIdBurger(int idBurger) {
            this.idBurger = idBurger;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public double getPrix() {
            return prix;
        }

        public void setPrix(double prix) {
            this.prix = prix;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<Ingredient> getIngredient() {
            return ingredient;
        }

        public void setIngredient(List<Ingredient> ingredient) {
            this.ingredient = ingredient;
        }
    }

    public static class Ingredient {

        @SerializedName("idIngredient")
        private int idIngredient;

        @SerializedName("prix")
        private double prix;

        @SerializedName("nom")
        private String nom;

        public int getIdIngredient() {
            return idIngredient;
        }

        public void setIdIngredient(int idIngredient) {
            this.idIngredient = idIngredient;
        }

        public double getPrix() {
            return prix;
        }

        public void setPrix(double prix) {
            this.prix = prix;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }
    }

    public static class Boisson {

        @SerializedName("idBoisson")
        private int idBoisson;

        @SerializedName("nom")
        private String nom;

        @SerializedName("prix")
        private double prix;

        @SerializedName("taille")
        private String taille;

        @SerializedName("image")
        private String image;

        public int getIdBoisson() {
            return idBoisson;
        }

        public void setIdBoisson(int idBoisson) {
            this.idBoisson = idBoisson;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public double getPrix() {
            return prix;
        }

        public void setPrix(double prix) {
            this.prix = prix;
        }

        public String getTaille() {
            return taille;
        }

        public void setTaille(String taille) {
            this.taille = taille;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}