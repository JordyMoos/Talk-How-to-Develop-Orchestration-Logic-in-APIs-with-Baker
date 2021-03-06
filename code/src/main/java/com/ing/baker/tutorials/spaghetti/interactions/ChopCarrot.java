package com.ing.baker.tutorials.spaghetti.interactions;

import com.ing.baker.recipe.annotations.FiresEvent;
import com.ing.baker.recipe.annotations.RequiresIngredient;
import com.ing.baker.recipe.javadsl.Interaction;
import com.ing.baker.tutorials.spaghetti.interactions.events.ChopCarrotEvents;
import com.ing.baker.tutorials.spaghetti.interactions.events.ChopCarrotEvents.ChopCarrotOutcome;
import com.ing.baker.tutorials.spaghetti.interactions.events.ChopCarrotEvents.CarrotChopped;
import com.ing.baker.tutorials.spaghetti.interactions.events.ChopCarrotEvents.FingerCut;

public interface ChopCarrot extends Interaction {

    @FiresEvent(oneOf = {CarrotChopped.class, FingerCut.class})
    ChopCarrotOutcome apply(@RequiresIngredient("carrot") int carrot,
                            @RequiresIngredient("knife") String knife) throws Exception;
}

