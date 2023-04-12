package fr.stvenchg.bleatz.api.orders;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import fr.stvenchg.bleatz.OrderWrapper;

public class OrdersResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("orders")
    private List<OrderWrapper> orders;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<OrderWrapper> getOrders() {
        return orders;
    }
}