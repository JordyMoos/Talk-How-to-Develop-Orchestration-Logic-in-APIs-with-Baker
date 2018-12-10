package com.ing.baker.tutorials.spaghetti.interactions.events;

import lombok.Value;

public class CreateRaguEvents {
    public interface CreateRaguOutcome {

    }

    @Value
    public static class RaguCreated implements CreateRaguOutcome {
        private int ragu;
    }
}
