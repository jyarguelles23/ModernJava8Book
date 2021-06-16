package com.viqsystems.Clases.Apple;

// Strategy Pattern
/*which lets you define a family of algorithms, encapsulate each algorithm
(called a strategy), and select an algorithm at run time. In this case the family of algorithms is
 ApplePredicate and the different strategies are AppleHeavyWeightPredicate
and AppleGreenColorPredicate*/

public interface ApplePredicate {
    boolean test (Apple apple);
}
