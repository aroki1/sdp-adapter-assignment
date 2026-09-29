package com.example.kitchentimer;

public final class ConsoleAlertChannel implements AlertChannel {
    @Override
    public void send(Alert alert) {
        System.out.printf("[%s] %s%n", alert.title(), alert.message());
    }
}
