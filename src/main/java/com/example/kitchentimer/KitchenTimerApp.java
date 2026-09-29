package com.example.kitchentimer;

import com.example.kitchentimer.legacy.LegacyKitchenBuzzer;

import java.util.Arrays;
import java.util.List;

public final class KitchenTimerApp {
    private KitchenTimerApp() {
    }

    public static void main(String[] args) throws AlertDeliveryException {
        List<String> options = Arrays.asList(args);
        KitchenContext context = new KitchenContext(
                options.contains("--phone"),
                options.contains("--buzzer"),
                1,
                options.contains("--quiet"));

        AlertChannelRouter router = new AlertChannelRouter(List.of(
                new PhoneChannelProvider(),
                new BuzzerChannelProvider(new LegacyKitchenBuzzer()),
                new ConsoleChannelProvider()));
        AlertChannel channel = router.select(context);

        if (options.contains("--recipe")) {
            RecipeTimer timer = new RecipeTimer(
                    "Чай", List.of("закипятить воду", "заварить чай"), channel);
            timer.finishCurrentStep();
            timer.finishCurrentStep();
        } else {
            new SingleStepTimer("заварить чай", channel).finish();
        }
    }
}
