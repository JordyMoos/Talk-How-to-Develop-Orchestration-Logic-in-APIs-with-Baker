package com.ing.baker.tutorials.spaghetti.interactions.events;

import lombok.Value;

public class ServeSpaghettiEvents {
    public interface ServeSpaghettiOutcome {

    }

    @Value
    public static class HappyCustomer implements ServeSpaghettiOutcome {

    }

    @Value
    public static class SadCustomer implements ServeSpaghettiOutcome {

    }
}
