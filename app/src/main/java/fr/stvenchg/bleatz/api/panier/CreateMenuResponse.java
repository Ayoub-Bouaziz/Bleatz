package fr.stvenchg.bleatz.api.panier;

import com.google.gson.annotations.SerializedName;

public class CreateMenuResponse {

        @SerializedName("success")
        private boolean success;

        @SerializedName("message")
        private String message;

        @SerializedName("idMenu")
        private int idMenu;

        @SerializedName("idBurger")
        private int idBurger;

        @SerializedName("idBoisson")
        private int idBoisson;

        @SerializedName("prix")
        private double prix;

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public int getIdMenu() {
            return idMenu;
        }

        public int getIdBurger() {
            return idBurger;
        }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public int getIdBoisson() {
            return idBoisson;
        }

        public double getPrix() {
            return prix;
        }
    }


