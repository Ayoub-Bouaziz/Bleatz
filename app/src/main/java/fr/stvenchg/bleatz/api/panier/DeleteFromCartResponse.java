package fr.stvenchg.bleatz.api.panier;

import com.google.gson.annotations.SerializedName;

public class DeleteFromCartResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("idPanier")
    private int idPanier;

    @SerializedName("idMenu")
    private String idMenu;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getIdPanier() {
        return idPanier;
    }

    public String getIdMenu() {
        return idMenu;
    }
}
