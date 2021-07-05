package com.viqsystems.Streams;

import com.viqsystems.Clases.Dish.Dish;
import com.viqsystems.Clases.Trader.Trader;
import com.viqsystems.Clases.Transaction.Transaction;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class StreamCollectionExamples {
    public enum CaloricLevel { DIET, NORMAL, FAT }
    Map<String, List<String>> dishTags = new HashMap<>();
    List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));
    List<Dish> specialMenu = Arrays.asList(
            new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER));

    public void countHowManyDishesMenuHas(){
        //the counting collector can be useful when used in combination with other collectors
        long howManyDishes = menu.stream().collect(Collectors.counting());

        long howManyDishes2 = menu.stream().count();
    }

    public void highestCalorieDish(){
        Comparator<Dish> dishCaloriesComparator =
                comparingInt(Dish::getCalories);
        Optional<Dish> mostCalorieDish =
                menu.stream()
                        .collect(maxBy(dishCaloriesComparator));
        Optional<Dish> mostCalorieDish1 =
                menu.stream()
                        .max(dishCaloriesComparator);
     //con reducing
        int totalCalories = menu.stream().collect(reducing(0,
                Dish::getCalories,
                Integer::sum));

        int totalCalories2 = menu.stream().map(Dish::getCalories).reduce(0, Integer::sum);

        int totalCalories3 =
                menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();

        int totalCalories4 = menu.stream().mapToInt(Dish::getCalories).sum();
    }

    public void Summarization (){
        // Each process has also Long,Double Types
        int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));
        int totalCalories2 = menu.stream().mapToInt(Dish::getCalories).sum();

        double avgCalories =
                menu.stream().collect(averagingInt(Dish::getCalories));
         // trae todo min,max,sum,ave,count
        IntSummaryStatistics menuStatistics =
                menu.stream().collect(summarizingInt(Dish::getCalories));

    }

    public void JoiningStrings (){
        /*The collector returned by the joining factory method concatenates into a single string*/
        String shortMenu = menu.stream().map(Dish::getName).collect(joining());

        String shortMenuSeparadoComa = menu.stream().map(Dish::getName).collect(joining(", "));

        String shortMenuReduce = menu.stream().map(Dish::getName)
                .collect( reducing( (s1, s2) -> s1 + s2 ) ).get();

        String shortMenuReducing = menu.stream()
                .collect( reducing( "", Dish::getName, (s1, s2) -> s1 + s2 ) );
    }

    public void Grouping(){
        Map<Dish.Type, List<Dish>> dishesByType =
                menu.stream().collect(groupingBy(Dish::getType));

        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect(
                groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                } ));
    }

    public void manipulatingGroupedElements(){
        //{OTHER=[french fries, pizza], MEAT=[pork, beef], FISH=[]}
        Map<Dish.Type, List<Dish>> caloricDishesByType =
                menu.stream()
                        .collect(groupingBy(Dish::getType,
                                filtering(dish -> dish.getCalories() > 500, toList())));

        Map<Dish.Type, List<String>> dishNamesByType =
                menu.stream()
                        .collect(groupingBy(Dish::getType,
                                mapping(Dish::getName, toList())));

       // In case you are required to extract these tags for each group of type of dishes you can
        // easily achieve this using the flatMapping Collector:

        Map<Dish.Type, Set<String>> dishNamesByTypeFlatMap =
                menu.stream()
                        .collect(groupingBy(Dish::getType,
                                flatMapping(dish -> dishTags.get( dish.getName() ).stream(),
                                        toSet())));
    }

    public void multiLevelGrouping(){
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel =
                menu.stream().collect(
                        groupingBy(Dish::getType,
                                groupingBy(dish -> {
                                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                                    else return CaloricLevel.FAT;
                                } )
                        )
                );
        // This multilevel grouping operation can be extended to any number of levels, and an n-level
        //grouping has as a result an n-level Map, modeling an n-level tree structure.

        Map<Dish.Type, Long> typesCount = menu.stream().collect(
                groupingBy(Dish::getType, counting()));
    }

    public void CollectingDataInSubgroups(){
        Map<Dish.Type, Optional<Dish>> mostCaloricByType =
                menu.stream()
                        .collect(groupingBy(Dish::getType,
                                maxBy(comparingInt(Dish::getCalories))));
        //{FISH=Optional[salmon], OTHER=Optional[pizza], MEAT=Optional[pork]}
        /*
        The values in this Map are Optionals because this is the resulting type
        of the collector generated by the maxBy factory method, but in reality if
        there’s no Dish in the menu for a given type, that type won’t have an
        Optional.empty() as value; it won’t be present at all as a key in the Map. The
        groupingBy collector lazily adds a new key in the grouping Map only the first
        time it finds an element in the stream, producing that key when applying on
        it the grouping criteria being used. This means that in this case, the Optional
        wrapper isn’t useful, because it’s not modeling a value that could be possibly
        absent but is there incidentally, only because this is the type returned by the
        reducing collector
        * */

        //ADAPTING THE COLLECTOR RESULT TO A DIFFERENT TYPE
        Map<Dish.Type, Dish> mostCaloricByType2 =
                menu.stream()
                        .collect(groupingBy(Dish::getType,
                                collectingAndThen(
                                        maxBy(comparingInt(Dish::getCalories)), Optional::get)));

        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType =
                menu.stream().collect(
                        groupingBy(Dish::getType, mapping(dish -> {
                                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                                    else return CaloricLevel.FAT; },
                                toSet() )));
    }

    public void partitioning(){
        //he fact that the partitioning function returns a boolean means the resulting
        // grouping Map will have a Boolean as a key type, and therefore,
        //there can be at most two different groups—one for true and one for false.
        Map<Boolean, List<Dish>> partitionedMenu =
                menu.stream().collect(partitioningBy(Dish::isVegetarian));

        Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType =
                menu.stream().collect(
                        partitioningBy(Dish::isVegetarian,
                                groupingBy(Dish::getType)));
        /*This will produce a two-level Map:
            {false={FISH=[prawns, salmon], MEAT=[pork, beef, chicken]},
            true={OTHER=[french fries, rice, season fruit, pizza]}}*/


        Map<Boolean, Dish> mostCaloricPartitionedByVegetarian =
                menu.stream().collect(
                        partitioningBy(Dish::isVegetarian,
                                collectingAndThen(maxBy(comparingInt(Dish::getCalories)),
                                        Optional::get)));
    }

      // customCollections
        public Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
            return IntStream.rangeClosed(2, n).boxed()
                    .collect(new PrimeNumbersCollector());
        }


}
