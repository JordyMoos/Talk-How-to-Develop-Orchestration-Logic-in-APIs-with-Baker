package com.ing.baker.tutorials.spaghetti.interactions.events;

import lombok.Value;

public class FryVegetablesEvents {
    public interface FryVegetablesOutcome {

    }

    @Value
    public static class VegetablesFried implements FryVegetablesOutcome {
        private int friedVegetables;
    }
}
