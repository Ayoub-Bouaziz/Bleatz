package fr.stvenchg.bleatz.api.kitchen;

import com.google.gson.annotations.SerializedName;
public class ValidateOrderResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("idCommande")
    private int idCommande;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }
}