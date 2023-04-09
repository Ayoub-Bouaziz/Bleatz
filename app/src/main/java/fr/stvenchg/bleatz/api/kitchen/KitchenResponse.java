package fr.stvenchg.bleatz.api.kitchen;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KitchenResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("orders")
    private List<Order> orders;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public class Order {

        @SerializedName("idCommande")
        private int idCommande;

        @SerializedName("idUser")
        private int idUser;

        @SerializedName("dateCommande")
        private String dateCommande;

        @SerializedName("statut")
        private String statut;

        @SerializedName("content")
        private List<CommandeContent> content;

        public int getIdCommande() {
            return idCommande;
        }

        public int getIdUser() {
            return idUser;
        }

        public String getDateCommande() {
            return dateCommande;
        }

        public String getStatut() {
            return statut;
        }

        public List<CommandeContent> getContent() {
            return content;
        }
    }

    public class CommandeContent {

        @SerializedName("idCommande")
        private int idCommande;

        @SerializedName("idMenu")
        private int idMenu;

        public int getIdCommande() {
            return idCommande;
        }

        public int getIdMenu() {
            return idMenu;
        }
    }
}
