package com.example.kitchentimer;

import com.example.kitchentimer.legacy.LegacyKitchenBuzzer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class AlertChannelRouterTest {
    @Test
    void choosesPhoneFromRuntimeKitchenContext() {
        AlertChannelRouter router = new AlertChannelRouter(List.of(
                new PhoneChannelProvider(),
                new BuzzerChannelProvider(new LegacyKitchenBuzzer()),
                new ConsoleChannelProvider()));

        AlertChannel selected = router.select(new KitchenContext(true, true, 2, false));

        assertInstanceOf(PhoneNotificationChannel.class, selected);
    }

    @Test
    void quietHoursSkipSoundAndUseConsoleFallback() {
        AlertChannelRouter router = new AlertChannelRouter(List.of(
                new PhoneChannelProvider(),
                new BuzzerChannelProvider(new LegacyKitchenBuzzer()),
                new ConsoleChannelProvider()));

        AlertChannel selected = router.select(new KitchenContext(true, true, 2, true));

        assertInstanceOf(ConsoleAlertChannel.class, selected);
    }
}
