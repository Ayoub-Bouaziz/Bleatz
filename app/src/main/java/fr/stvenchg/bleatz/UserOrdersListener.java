package fr.stvenchg.bleatz;

import java.util.List;

public interface UserOrdersListener {
    void onOrdersFetched(List<Order> processingOrders, List<Order> finishedOrders);
}
