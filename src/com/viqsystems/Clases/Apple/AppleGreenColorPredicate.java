package com.viqsystems.Clases.Apple;

import static com.viqsystems.Enums.Colors.GREEN;

public class AppleGreenColorPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return GREEN.equals(apple.getColor());
    }
}
