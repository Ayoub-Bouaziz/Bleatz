package fr.stvenchg.bleatz.api.panier;

import com.google.gson.annotations.SerializedName;

public class AddToCartRequest {
    @SerializedName("idMenu")
    private int menuId;

    public AddToCartRequest(int menuId) {
        this.menuId = menuId;
    }
}
