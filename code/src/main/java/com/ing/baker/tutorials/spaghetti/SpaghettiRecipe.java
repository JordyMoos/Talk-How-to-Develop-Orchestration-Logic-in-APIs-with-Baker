package com.ing.baker.tutorials.spaghetti;

import com.ing.baker.recipe.javadsl.Recipe;
import com.ing.baker.tutorials.spaghetti.interactions.*;
import com.ing.baker.tutorials.spaghetti.interactions.events.GatheredKitchenware;
import com.ing.baker.tutorials.spaghetti.interactions.events.ShoppedGroceries;

import static com.ing.baker.recipe.javadsl.InteractionDescriptor.of;

class SpaghettiRecipe {
    static Recipe getRecipe() {
        return new Recipe("Spaghetti")
                .withInteractions(
                    of(BoilPasta.class),
                    of(ChopCarrot.class),
                    of(ChopOnion.class),
                    of(CreateRagu.class),
                    of(FryMeat.class),
                    of(FryVegetables.class),
                    of(ServeSpaghetti.class)
                )
                .withSensoryEvents(
                    ShoppedGroceries.class,
                    GatheredKitchenware.class
                );
    }
}
