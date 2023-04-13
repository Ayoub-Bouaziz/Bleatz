package fr.stvenchg.bleatz.api.composer;

import com.google.gson.annotations.SerializedName;

public class AddComposerResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("idIngredient")
    private int idIngredient;

    @SerializedName("idBurger")
    private int idBurger;

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

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    public int getIdBurger() {
        return idBurger;
    }

    public void setIdBurger(int idBurger) {
        this.idBurger = idBurger;
    }
}
