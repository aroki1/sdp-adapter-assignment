package com.example.kitchentimer;

public interface AlertChannelProvider {
    boolean supports(KitchenContext context);

    AlertChannel create(KitchenContext context);
}
