package com.example.kitchentimer;

import com.example.kitchentimer.legacy.LegacyKitchenBuzzer;

import java.util.Objects;

public final class BuzzerChannelProvider implements AlertChannelProvider {
    private final LegacyKitchenBuzzer buzzer;

    public BuzzerChannelProvider(LegacyKitchenBuzzer buzzer) {
        this.buzzer = Objects.requireNonNull(buzzer, "buzzer");
    }

    @Override
    public boolean supports(KitchenContext context) {
        return context.buzzerAvailable() && !context.quietHours();
    }

    @Override
    public AlertChannel create(KitchenContext context) {
        return new LegacyKitchenBuzzerAdapter(buzzer, context.buzzerOutputNumber());
    }
}
