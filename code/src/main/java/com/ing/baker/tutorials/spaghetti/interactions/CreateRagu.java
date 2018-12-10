package com.ing.baker.tutorials.spaghetti.interactions;

import com.ing.baker.recipe.annotations.FiresEvent;
import com.ing.baker.recipe.annotations.RequiresIngredient;
import com.ing.baker.recipe.javadsl.Interaction;
import com.ing.baker.tutorials.spaghetti.interactions.events.CreateRaguEvents.CreateRaguOutcome;
import com.ing.baker.tutorials.spaghetti.interactions.events.CreateRaguEvents.RaguCreated;

public interface CreateRagu extends Interaction {

    @FiresEvent(oneOf = {RaguCreated.class})
    CreateRaguOutcome apply(@RequiresIngredient("fryingPan") String fryingPan,
                            @RequiresIngredient("friedMeatAndVegetables") int friedMeat,
                            @RequiresIngredient("tomatoPaste") int tomatoPaste,
                            @RequiresIngredient("wine") int wine,
                            @RequiresIngredient("herbs") int herbs) throws Exception;
}
