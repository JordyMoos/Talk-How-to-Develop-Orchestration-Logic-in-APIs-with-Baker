package com.ing.baker.tutorials.spaghetti.interactions;

import com.ing.baker.recipe.annotations.FiresEvent;
import com.ing.baker.recipe.annotations.RequiresIngredient;
import com.ing.baker.recipe.javadsl.Interaction;
import com.ing.baker.tutorials.spaghetti.interactions.events.FryVegetablesEvents.FryVegetablesOutcome;
import com.ing.baker.tutorials.spaghetti.interactions.events.FryVegetablesEvents.VegetablesFried;

public interface FryVegetables extends Interaction {

    @FiresEvent(oneOf = {VegetablesFried.class})
    FryVegetablesOutcome apply(@RequiresIngredient("fryingPan") String fryingPan,
                               @RequiresIngredient("choppedCarrot") int choppedCarrot,
                               @RequiresIngredient("choppedOnion") int choppedOnion) throws Exception;
}
