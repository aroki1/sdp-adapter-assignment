package com.example.kitchentimer;

import java.util.Objects;

public final class SingleStepTimer extends KitchenTimer {
    private final String stepName;

    public SingleStepTimer(String stepName, AlertChannel alertChannel) {
        super(alertChannel);
        this.stepName = Objects.requireNonNull(stepName, "stepName");
    }

    public void finish() throws AlertDeliveryException {
        notify("Таймер готов", "Закончился этап: " + stepName, 1);
    }
}
