package com.example.kitchentimer;

public final class ConsoleChannelProvider implements AlertChannelProvider {
    @Override
    public boolean supports(KitchenContext context) {
        return true;
    }

    @Override
    public AlertChannel create(KitchenContext context) {
        return new ConsoleAlertChannel();
    }
}
