# Design Rationale

## Problem domain

The project models a timer for a shared kitchen. A user can time one cooking step or move through the steps of a recipe. When a step finishes, the application sends an alert through a device available in the kitchen.

## Why Bridge and Adapter are both needed

Bridge separates two dimensions that can change independently. `SingleStepTimer` and `RecipeTimer` are the Abstraction hierarchy; alert delivery channels are the Implementor hierarchy. Timers depend only on `AlertChannel`. A new timer type can therefore use existing channels, and a new channel can be added without changing the timer classes.

Adapter solves a separate compatibility problem. `LegacyKitchenBuzzer` does not implement `AlertChannel`: its native method accepts an output number and a byte array of pulses, returns numeric status codes, and may throw a device-specific exception. `LegacyKitchenBuzzerAdapter` converts an alert into a pulse pattern and translates status codes and exceptions into `AlertDeliveryException`. It does not expose the legacy status code, exception type, or exception message, and the buzzer API itself is left unchanged.

Bridge alone would define the common channel contract but would not make the incompatible buzzer conform to it. Adapter alone could connect the buzzer, but it would not separate the timer types from the delivery channels; supporting every timer-device combination would require classes for those combinations.

## Required complexity module: dynamic implementor selection

`AlertChannelRouter` selects a channel at runtime from `KitchenContext`, which describes phone and buzzer availability and whether quiet hours are active. Providers are checked in order, with the console as a fallback. The application passes the selected channel to a timer instead of choosing a concrete channel inside the timer.

## Limitation

The legacy buzzer can only produce sound, so the adapter cannot deliver the alert text through it. It represents normal and elevated priority with different pulse patterns. Full alert text is available only through channels that can display it.
