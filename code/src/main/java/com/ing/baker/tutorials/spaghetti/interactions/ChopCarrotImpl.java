package com.ing.baker.tutorials.spaghetti.interactions;

import com.ing.baker.tutorials.spaghetti.interactions.events.ChopCarrotEvents.CarrotChopped;
import com.ing.baker.tutorials.spaghetti.interactions.events.ChopCarrotEvents.ChopCarrotOutcome;

public class ChopCarrotImpl implements ChopCarrot {

    @Override
    public ChopCarrotOutcome apply(int carrot, String knife) throws Exception {
        System.out.println("Chopping these carrots like a pro");
        return new CarrotChopped(100);
    }
}

