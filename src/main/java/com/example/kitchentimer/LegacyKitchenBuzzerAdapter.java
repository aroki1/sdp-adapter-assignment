package com.example.kitchentimer;

import com.example.kitchentimer.legacy.LegacyHardwareException;
import com.example.kitchentimer.legacy.LegacyKitchenBuzzer;

import java.util.Objects;

public final class LegacyKitchenBuzzerAdapter implements AlertChannel {
    private static final byte[] NORMAL_PATTERN = {1, 0, 1};
    private static final byte[] URGENT_PATTERN = {1, 1, 0, 1, 1};

    private final LegacyKitchenBuzzer buzzer;
    private final int outputNumber;

    public LegacyKitchenBuzzerAdapter(LegacyKitchenBuzzer buzzer, int outputNumber) {
        this.buzzer = Objects.requireNonNull(buzzer, "buzzer");
        this.outputNumber = outputNumber;
    }

    @Override
    public void send(Alert alert) throws AlertDeliveryException {
        byte[] pattern = alert.priority() >= 2 ? URGENT_PATTERN : NORMAL_PATTERN;
        final int status;
        try {
            status = buzzer.pulse(outputNumber, pattern.clone());
        } catch (LegacyHardwareException exception) {
            throw new AlertDeliveryException("Не удалось включить кухонный зуммер: сбой оборудования.");
        } catch (RuntimeException exception) {
            throw new AlertDeliveryException("Не удалось включить кухонный зуммер: сбой оборудования.");
        }

        if (status == LegacyKitchenBuzzer.OK) {
            return;
        }
        if (status == LegacyKitchenBuzzer.INVALID_OUTPUT) {
            throw new AlertDeliveryException("Не удалось включить кухонный зуммер: проверьте подключение.");
        }
        if (status == LegacyKitchenBuzzer.DEVICE_BUSY) {
            throw new AlertDeliveryException("Не удалось включить кухонный зуммер: устройство занято.");
        }
        throw new AlertDeliveryException("Не удалось включить кухонный зуммер: неизвестная ошибка устройства.");
    }
}
