package fr.stvenchg.bleatz.listener;

import fr.stvenchg.bleatz.api.kitchen.ValidateOrderResponse;

public interface ValidateOrderListener {
    void onOrderValidated(ValidateOrderResponse response);
    void onOrderValidationFailed(Throwable t);
}