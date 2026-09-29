package com.example.kitchentimer;

import com.example.kitchentimer.legacy.LegacyHardwareException;
import com.example.kitchentimer.legacy.LegacyKitchenBuzzer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LegacyKitchenBuzzerAdapterTest {
    @Test
    void convertsAlertToLegacyOutputAndPulsePattern() throws Exception {
        CapturingBuzzer buzzer = new CapturingBuzzer();
        LegacyKitchenBuzzerAdapter adapter = new LegacyKitchenBuzzerAdapter(buzzer, 4);

        adapter.send(new Alert("Готово", "Время вышло", 2));

        assertEquals(4, buzzer.outputNumber);
        assertEquals("[1, 1, 0, 1, 1]", buzzer.pattern);
    }

    @Test
    void translatesLegacyStatusCodeToContractException() {
        LegacyKitchenBuzzer buzzer = new StubBuzzer(LegacyKitchenBuzzer.DEVICE_BUSY);
        LegacyKitchenBuzzerAdapter adapter = new LegacyKitchenBuzzerAdapter(buzzer, 4);

        AlertDeliveryException exception = assertThrows(AlertDeliveryException.class,
                () -> adapter.send(new Alert("Готово", "Выключите плиту", 1)));

        assertEquals("Не удалось включить кухонный зуммер: устройство занято.", exception.getMessage());
        assertFalse(exception.getMessage().contains(String.valueOf(LegacyKitchenBuzzer.DEVICE_BUSY)));
        assertFalse(exception.getMessage().contains(LegacyHardwareException.class.getSimpleName()));
    }

    @Test
    void translatesLegacyExceptionWithoutLeakingItsTypeOrMessage() {
        LegacyKitchenBuzzer buzzer = new ThrowingStubBuzzer();
        LegacyKitchenBuzzerAdapter adapter = new LegacyKitchenBuzzerAdapter(buzzer, 4);

        AlertDeliveryException exception = assertThrows(AlertDeliveryException.class,
                () -> adapter.send(new Alert("Готово", "Время вышло", 2)));

        assertEquals("Не удалось включить кухонный зуммер: сбой оборудования.", exception.getMessage());
        assertFalse(exception.getMessage().contains("legacy failure"));
        assertTrue(exception.getCause() == null);
    }

    private static class StubBuzzer extends LegacyKitchenBuzzer {
        private final int result;

        private StubBuzzer(int result) {
            this.result = result;
        }

        @Override
        public int pulse(int outputNumber, byte[] pulsePattern) {
            return result;
        }
    }

    private static final class CapturingBuzzer extends LegacyKitchenBuzzer {
        private int outputNumber;
        private String pattern;

        @Override
        public int pulse(int outputNumber, byte[] pulsePattern) {
            this.outputNumber = outputNumber;
            this.pattern = java.util.Arrays.toString(pulsePattern);
            return OK;
        }
    }

    private static final class ThrowingStubBuzzer extends LegacyKitchenBuzzer {
        @Override
        public int pulse(int outputNumber, byte[] pulsePattern) throws LegacyHardwareException {
            throw new LegacyHardwareException("legacy failure details");
        }
    }
}
