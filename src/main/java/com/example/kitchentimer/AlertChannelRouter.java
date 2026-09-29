package com.example.kitchentimer;

import java.util.List;
import java.util.Objects;

public final class AlertChannelRouter {
    private final List<AlertChannelProvider> providers;

    public AlertChannelRouter(List<AlertChannelProvider> providers) {
        this.providers = List.copyOf(providers);
    }

    public AlertChannel select(KitchenContext context) {
        Objects.requireNonNull(context, "context");
        return providers.stream()
                .filter(provider -> provider.supports(context))
                .findFirst()
                .map(provider -> provider.create(context))
                .orElseThrow(() -> new IllegalStateException("Нет доступного способа подать сигнал"));
    }
}
