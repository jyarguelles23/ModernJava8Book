package com.viqsystems.MethodReferences;

import com.viqsystems.Clases.Apple.Apple;
import com.viqsystems.Enums.Colors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.*;

import static com.viqsystems.Enums.Colors.GREEN;
import static com.viqsystems.Enums.Colors.RED;
import static java.util.Comparator.comparing;

public class MethodReferences {

    List<Apple> inventory = Arrays.asList(new Apple(80, GREEN),
            new Apple(155, GREEN),
            new Apple(120, RED));

    //Method references
    public void sortMEthodReference(){
        List<String> str = Arrays.asList("a","b","A","B");
        str.sort(String::compareToIgnoreCase);

        inventory.sort(comparing(Apple::getWeight));
        inventory.forEach(i -> System.out.println(i.getWeight()));
        inventory.sort(comparing(Apple::getWeight).reversed());
        inventory.forEach(i -> System.out.println(i.getWeight()));

        //Si los pesos son iguales comparar por color
        inventory.sort(comparing(Apple::getWeight)
                         .reversed()
                         .thenComparing(Apple::getColor));
    }



    ToIntFunction<String> stringToInt = (String s) -> Integer.parseInt(s);
    ToIntFunction<String> stringToInt2 = Integer::parseInt;

    BiPredicate<List<String>, String> contains =
            (list, element) -> list.contains(element);
    BiPredicate<List<String>, String> contains2= List::contains;


    //for creating elements
    Supplier<Apple> c1 = Apple::new;
    Apple a1 = c1.get();

    Function<Integer, Apple> c2 = Apple::new;
    Apple a2 = c2.apply(110);

    BiFunction< Integer, Colors, Apple> c3 = Apple::new;
    Apple a3 = c3.apply( 110,GREEN);

    List<Integer> weights = Arrays.asList(7, 3, 4, 10);

    List<Apple> apples = map(weights, Apple::new);

    public static List<Apple> map(List<Integer> list, Function<Integer, Apple> f) {
        List<Apple> result = new ArrayList<>();
        for(Integer i: list) {
            result.add(f.apply(i));
        }
        return result;
    }
}
