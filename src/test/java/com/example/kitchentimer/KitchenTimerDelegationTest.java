package com.example.kitchentimer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KitchenTimerDelegationTest {
    @Test
    void singleStepTimerDelegatesAlertToImplementor() throws Exception {
        RecordingAlertChannel channel = new RecordingAlertChannel();
        SingleStepTimer timer = new SingleStepTimer("поставить чайник", channel);

        timer.finish();

        assertEquals(List.of(new Alert("Таймер готов", "Закончился этап: поставить чайник", 1)),
                channel.alerts);
    }

    @Test
    void recipeTimerDelegatesEachStepToImplementor() throws Exception {
        RecordingAlertChannel channel = new RecordingAlertChannel();
        RecipeTimer timer = new RecipeTimer("Овсянка", List.of("закипятить воду", "добавить хлопья"), channel);

        timer.finishCurrentStep();
        timer.finishCurrentStep();

        assertEquals(List.of(
                new Alert("Этап рецепта завершён", "Овсянка: закипятить воду", 1),
                new Alert("Этап рецепта завершён", "Овсянка: добавить хлопья", 1)), channel.alerts);
        assertTrue(timer.isComplete());
    }

    private static final class RecordingAlertChannel implements AlertChannel {
        private final List<Alert> alerts = new ArrayList<>();

        @Override
        public void send(Alert alert) {
            alerts.add(alert);
        }
    }
}
