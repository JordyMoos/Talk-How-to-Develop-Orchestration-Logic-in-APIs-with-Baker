package com.ing.baker.tutorials.spaghetti.interactions.events;

import lombok.Value;

public class FryMeatEvents {
    public interface FryMeatOutcome {

    }

    @Value
    public static class MeatFried implements FryMeatOutcome {
        private int friedMeatAndVegetables;
    }
}
