package com.example.kitchentimer;

public final class PhoneChannelProvider implements AlertChannelProvider {
    @Override
    public boolean supports(KitchenContext context) {
        return context.phoneAvailable() && !context.quietHours();
    }

    @Override
    public AlertChannel create(KitchenContext context) {
        return new PhoneNotificationChannel();
    }
}
