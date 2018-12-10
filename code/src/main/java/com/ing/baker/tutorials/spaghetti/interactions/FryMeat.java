package com.ing.baker.tutorials.spaghetti.interactions;

import com.ing.baker.recipe.annotations.FiresEvent;
import com.ing.baker.recipe.annotations.RequiresIngredient;
import com.ing.baker.recipe.javadsl.Interaction;
import com.ing.baker.tutorials.spaghetti.interactions.events.FryMeatEvents.FryMeatOutcome;
import com.ing.baker.tutorials.spaghetti.interactions.events.FryMeatEvents.MeatFried;

public interface FryMeat extends Interaction {

    @FiresEvent(oneOf = {MeatFried.class})
    FryMeatOutcome apply(@RequiresIngredient("fryingPan") String fryingPan,
                         @RequiresIngredient("friedVegetables") int friedVegetables,
                         @RequiresIngredient("mincedMeat") int mincedMeat) throws Exception;
}
