package fr.stvenchg.bleatz.api;

import java.util.List;
import fr.stvenchg.bleatz.api.panier.CartResponse;
import fr.stvenchg.bleatz.api.panier.CartResponse.MenuContent;

public class CartResponseWrapper {
    private boolean success;
    private String message;
    private int idPanier;
    private double total_price;
    private int menu_count;
    private List<MenuContent> content;

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public int getIdPanier() {
        return idPanier;
    }

    public double getTotal_price() {
        return total_price;
    }

    public int getMenu_count() {
        return menu_count;
    }

    public List<MenuContent> getContent() {
        return content;
    }

    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setIdPanier(int idPanier) {
        this.idPanier = idPanier;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public void setMenu_count(int menu_count) {
        this.menu_count = menu_count;
    }

    public void setContent(List<MenuContent> content) {
        this.content = content;
    }
}