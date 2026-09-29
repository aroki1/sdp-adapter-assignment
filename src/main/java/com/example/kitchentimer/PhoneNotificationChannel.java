package com.example.kitchentimer;

public final class PhoneNotificationChannel implements AlertChannel {
    @Override
    public void send(Alert alert) {
        // A real application would call the phone's notification service here.
        System.out.printf("Уведомление на телефон: %s — %s%n", alert.title(), alert.message());
    }
}
