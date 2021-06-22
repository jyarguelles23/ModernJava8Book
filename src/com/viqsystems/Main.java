package com.viqsystems;

import com.viqsystems.Clases.Apple.Apple;
import com.viqsystems.Clases.Apple.AppleGreenColorPredicate;
import com.viqsystems.Clases.Apple.AppleHeavyWeightPredicate;
import com.viqsystems.Clases.Apple.ApplePredicate;
import com.viqsystems.Clases.Trader.Trader;
import com.viqsystems.Clases.Transaction.Transaction;
import com.viqsystems.Enums.Colors;

import java.awt.*;
import java.awt.event.WindowFocusListener;
import java.util.*;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        Comparator<Apple> byColor= (Apple a1, Apple a2) -> {
            return a2.getColor().compareTo(a1.getColor());
        };

        List<Apple> greenApplesWithLambda =
                filterApples(inventory, a -> GREEN.equals(a.getColor()));

        //con lambdas
        List<String> str = Arrays.asList("a","b","A","B");
        str.sort((s1, s2) -> s1.compareToIgnoreCase(s2));

        //for streams
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        Stream.iterate(new int[]{0, 1},t -> new int[]{t[1], t[0]+t[1]})
                 .limit(20)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] +")"));

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


