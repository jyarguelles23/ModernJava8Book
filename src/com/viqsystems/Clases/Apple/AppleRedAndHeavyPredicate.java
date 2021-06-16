package com.viqsystems.Clases.Apple;

import static com.viqsystems.Enums.Colors.RED;

public class AppleRedAndHeavyPredicate implements ApplePredicate{

    public boolean test(Apple apple){
        return RED.equals(apple.getColor())
                && apple.getWeight() > 150;
    }
}
