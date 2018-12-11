package com.ing.baker.tutorials.spaghetti.interactions.events;

import lombok.Value;

public class ChopCarrotEvents {
    public interface ChopCarrotOutcome {

    }

    @Value
    public static class CarrotChopped implements ChopCarrotOutcome {
        private int choppedCarrot;
    }

    public static class FingerCut implements ChopCarrotOutcome {

    }
}





