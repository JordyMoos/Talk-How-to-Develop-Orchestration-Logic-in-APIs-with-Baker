package com.ing.baker.tutorials.spaghetti.interactions.events;

import lombok.Value;

public class BoilPastaEvents {
    public interface BoilPastaOutcome {

    }

    @Value
    public static class BoiledPasta implements BoilPastaOutcome {
        private int alDentePasta;
    }
}
