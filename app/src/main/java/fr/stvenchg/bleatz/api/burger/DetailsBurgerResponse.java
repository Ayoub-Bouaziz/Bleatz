package fr.stvenchg.bleatz.api.burger;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailsBurgerResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("burgers")
    @Expose
    private List<Burger> burgers;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Burger> getBurgers() {
        return burgers;
    }

    public void setBurgers(List<Burger> burgers) {
        this.burgers = burgers;
    }

    public static class Burger {

        @SerializedName("idBurger")
        @Expose
        private Integer idBurger;
        @SerializedName("nom")
        @Expose
        private String nom;
        @SerializedName("prix")
        @Expose
        private Double prix;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("ingredients")
        @Expose
        private List<Ingredient> ingredients;

        public Integer getIdBurger() {
            return idBurger;
        }

        public void setIdBurger(Integer idBurger) {
            this.idBurger = idBurger;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public Double getPrix() {
            return prix;
        }

        public void setPrix(Double prix) {
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

        public List<Ingredient> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
        }

        public class Ingredient {

            @SerializedName("idIngredient")
            @Expose
            private Integer idIngredient;
            @SerializedName("prix")
            @Expose
            private Double prix;
            @SerializedName("nom")
            @Expose
            private String nom;

            public Integer getIdIngredient() {
                return idIngredient;
            }

            public void setIdIngredient(Integer idIngredient) {
                this.idIngredient = idIngredient;
            }

            public Double getPrix() {
                return prix;
            }

            public void setPrix(Double prix) {
                this.prix = prix;
            }

            public String getNom() {
                return nom;
            }

            public void setNom(String nom) {
                this.nom = nom;
            }

        }

    }

}

