package com.example.kitchentimer;

import java.util.List;
import java.util.Objects;

public final class RecipeTimer extends KitchenTimer {
    private final String recipeName;
    private final List<String> steps;
    private int currentStep;

    public RecipeTimer(String recipeName, List<String> steps, AlertChannel alertChannel) {
        super(alertChannel);
        this.recipeName = Objects.requireNonNull(recipeName, "recipeName");
        this.steps = List.copyOf(steps);
        if (this.steps.isEmpty()) {
            throw new IllegalArgumentException("A recipe must have at least one step");
        }
    }

    public void finishCurrentStep() throws AlertDeliveryException {
        if (currentStep >= steps.size()) {
            throw new IllegalStateException("All recipe steps are already complete");
        }

        String stepName = steps.get(currentStep);
        notify("Этап рецепта завершён", recipeName + ": " + stepName, 1);
        currentStep++;
    }

    public boolean isComplete() {
        return currentStep == steps.size();
    }
}
