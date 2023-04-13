package fr.stvenchg.bleatz.listener;

import java.util.List;

import fr.stvenchg.bleatz.Order;

public interface UserOrdersListener {
    void onOrdersFetched(List<Order> processingOrders, List<Order> finishedOrders);
}
