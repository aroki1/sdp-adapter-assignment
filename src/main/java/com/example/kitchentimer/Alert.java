package com.example.kitchentimer;

import java.util.Objects;

public record Alert(String title, String message, int priority) {
    public Alert {
        Objects.requireNonNull(title, "title");
        Objects.requireNonNull(message, "message");
        if (priority < 1 || priority > 3) {
            throw new IllegalArgumentException("priority must be between 1 and 3");
        }
    }
}
