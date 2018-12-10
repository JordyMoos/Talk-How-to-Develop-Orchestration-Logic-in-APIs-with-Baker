package com.ing.baker.tutorials.spaghetti.interactions;

import com.ing.baker.recipe.annotations.FiresEvent;
import com.ing.baker.recipe.annotations.RequiresIngredient;
import com.ing.baker.recipe.javadsl.Interaction;
import com.ing.baker.tutorials.spaghetti.interactions.events.ServeSpaghettiEvents.ServeSpaghettiOutcome;
import com.ing.baker.tutorials.spaghetti.interactions.events.ServeSpaghettiEvents.HappyCustomer;
import com.ing.baker.tutorials.spaghetti.interactions.events.ServeSpaghettiEvents.SadCustomer;

public interface ServeSpaghetti extends Interaction {

    @FiresEvent(oneOf = {HappyCustomer.class, SadCustomer.class})
    ServeSpaghettiOutcome apply(@RequiresIngredient("alDentePasta") int alDentePasta,
                           @RequiresIngredient("ragu") int ragu) throws Exception;
}
