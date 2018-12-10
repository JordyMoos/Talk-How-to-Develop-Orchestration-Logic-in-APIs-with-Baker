package com.ing.baker.tutorials.spaghetti.interactions;

import com.ing.baker.recipe.annotations.FiresEvent;
import com.ing.baker.recipe.annotations.RequiresIngredient;
import com.ing.baker.recipe.javadsl.Interaction;
import com.ing.baker.tutorials.spaghetti.interactions.events.ChopOnionEvents.ChopOnionOutcome;
import com.ing.baker.tutorials.spaghetti.interactions.events.ChopOnionEvents.OnionChopped;

public interface ChopOnion extends Interaction {

    @FiresEvent(oneOf = {OnionChopped.class})
    ChopOnionOutcome apply(@RequiresIngredient("onion") int onion,
                           @RequiresIngredient("knife") String knife) throws Exception;
}
