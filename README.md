# Shared Kitchen Timer

A small Java project demonstrating the Bridge and Adapter patterns. Cooking timers report completed steps through an alert device selected at runtime.

## Requirements

- JDK 17 or newer
- Maven

## Run the application

Run the demo with console output:

```text
mvn exec:java
```

Pass options to demonstrate runtime channel selection and both timer types:

```text
mvn exec:java -Dexec.args="--phone --recipe"
mvn exec:java -Dexec.args="--buzzer"
mvn exec:java -Dexec.args="--quiet"
```

- `--phone` selects phone notifications unless quiet hours are active.
- `--buzzer` makes the legacy buzzer available; it is selected when the phone is unavailable.
- `--quiet` skips the phone and buzzer and uses the console fallback.
- `--recipe` runs two recipe steps. Without it, the app runs a single-step timer.

## Run the tests

```text
mvn test
```