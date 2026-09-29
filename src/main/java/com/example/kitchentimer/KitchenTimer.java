package com.example.kitchentimer;

import java.util.Objects;

public abstract class KitchenTimer {
    private final AlertChannel alertChannel;

    protected KitchenTimer(AlertChannel alertChannel) {
        this.alertChannel = Objects.requireNonNull(alertChannel, "alertChannel");
    }

    protected final void notify(String title, String message, int priority)
            throws AlertDeliveryException {
        alertChannel.send(new Alert(title, message, priority));
    }
}
