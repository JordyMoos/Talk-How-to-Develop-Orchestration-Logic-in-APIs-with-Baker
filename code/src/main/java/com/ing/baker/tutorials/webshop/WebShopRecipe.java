package com.ing.baker.tutorials.webshop;

import com.ing.baker.recipe.javadsl.Recipe;
import com.ing.baker.tutorials.webshop.interactions.ManufactureGoods;
import com.ing.baker.tutorials.webshop.interactions.SendInvoice;
import com.ing.baker.tutorials.webshop.interactions.ShipGoods;
import com.ing.baker.tutorials.webshop.interactions.ValidateOrder;
import com.ing.baker.tutorials.webshop.interactions.events.SensoryEvents.CustomerInfoReceived;
import com.ing.baker.tutorials.webshop.interactions.events.SensoryEvents.OrderPlaced;
import com.ing.baker.tutorials.webshop.interactions.events.SensoryEvents.PaymentMade;
import com.ing.baker.tutorials.webshop.interactions.events.ShipGoodsEvents.GoodsShipped;
import com.ing.baker.tutorials.webshop.interactions.events.ValidateOrderEvents.OrderValid;

import static com.ing.baker.recipe.javadsl.InteractionDescriptor.of;

class WebShopRecipe {
    static Recipe getRecipe() {
        //TODO implement the recipe according to the sequence diagram in docs/sequence.png
        return new Recipe("WebShop")
                .withInteractions(
                        of(ManufactureGoods.class).withRequiredEvents(OrderValid.class, PaymentMade.class),
                        of(SendInvoice.class).withRequiredEvents(GoodsShipped.class),
                        of(ShipGoods.class),
                        of(ValidateOrder.class)
                )
                .withSensoryEvents(
                        CustomerInfoReceived.class,
                        OrderPlaced.class,
                        PaymentMade.class
                );
    }
}
