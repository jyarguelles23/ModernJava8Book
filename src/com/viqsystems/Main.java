package com.viqsystems;

import com.viqsystems.Clases.Apple.Apple;
import com.viqsystems.Clases.Apple.AppleGreenColorPredicate;
import com.viqsystems.Clases.Apple.AppleHeavyWeightPredicate;
import com.viqsystems.Clases.Apple.ApplePredicate;
import com.viqsystems.Enums.Colors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.viqsystems.Enums.Colors.GREEN;
import static com.viqsystems.Enums.Colors.RED;

public class Main {

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(new Apple(80, GREEN),
                new Apple(155, GREEN),
                new Apple(120, RED));
        List<Apple> heavyApples =
                filterApples(inventory, new AppleHeavyWeightPredicate());
        List<Apple> greenApples =
                filterApples(inventory, new AppleGreenColorPredicate());

        

    }
    public static List<Apple> filterApples(List<Apple> inventory,
                                           ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }
   }

