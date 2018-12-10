package com.ing.baker.tutorials.spaghetti.interactions.events;

import lombok.Value;

public class ChopOnionEvents {
    public interface ChopOnionOutcome {

    }

    @Value
    public static class OnionChopped implements ChopOnionOutcome {
        private int choppedOnion;
    }
}
