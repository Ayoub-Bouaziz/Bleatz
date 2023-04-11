package fr.stvenchg.bleatz.api.boisson;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BoissonResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("boissons")
    private List<Boisson> boissons;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Boisson> getBoissons() {
        return boissons;
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

        public String getNom() {
            return nom;
        }

        public double getPrix() {
            return prix;
        }

        public String getTaille() {
            return taille;
        }

        public String getImage() {
            return image;
        }
    }

}
