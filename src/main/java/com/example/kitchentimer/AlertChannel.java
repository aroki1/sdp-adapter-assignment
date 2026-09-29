package com.example.kitchentimer;

public interface AlertChannel {
    void send(Alert alert) throws AlertDeliveryException;
}
