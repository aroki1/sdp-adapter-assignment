package com.example.kitchentimer;

public record KitchenContext(boolean phoneAvailable,
                             boolean buzzerAvailable,
                             int buzzerOutputNumber,
                             boolean quietHours) {
}
