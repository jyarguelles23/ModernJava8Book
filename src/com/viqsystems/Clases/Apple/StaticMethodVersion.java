package com.viqsystems.Clases.Apple;

import java.util.ArrayList;
import java.util.List;

public class StaticMethodVersion {


    public static List<Apple> filterApples4(List<Apple> inventory,
                                           ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for(Apple apple: inventory) {
            if(p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    private void Test(){
        List<Apple> inventory=new ArrayList<>();
        List<Apple> redAndHeavyApples =
                filterApples4( inventory
                        , new AppleRedAndHeavyPredicate());
    }
}
