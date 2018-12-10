package com.ing.baker.tutorials.spaghetti.interactions;

import com.ing.baker.recipe.annotations.FiresEvent;
import com.ing.baker.recipe.annotations.RequiresIngredient;
import com.ing.baker.recipe.javadsl.Interaction;
import com.ing.baker.tutorials.spaghetti.interactions.events.BoilPastaEvents.BoilPastaOutcome;
import com.ing.baker.tutorials.spaghetti.interactions.events.BoilPastaEvents.BoiledPasta;

public interface BoilPasta extends Interaction {

    @FiresEvent(oneOf = {BoiledPasta.class})
    BoilPastaOutcome apply(@RequiresIngredient("cookingPod") String cookingPod,
                         @RequiresIngredient("pasta") int pasta) throws Exception;
}
