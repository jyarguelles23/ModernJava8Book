package com.viqsystems.Streams;

import com.viqsystems.Clases.Dish.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

//Keep in mind that you can consume a stream only once!
public class StreamExamples {

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

    public void threeCaloricDishNames() {
        List<String> threeHighCaloricDishNames =
                menu.stream()
                        .filter(dish -> dish.getCalories() > 300)
                        .map(Dish::getName)
                        .limit(3)
                        .collect(toList());
        System.out.println(threeHighCaloricDishNames);
    }

    public void vegetarianDishesMethod() {
        List<Dish> vegetarianDishes =
                menu.stream()
                        .filter(Dish::isVegetarian)
                        .collect(toList());
    }

    /* Java 9 added two new methods that are useful for
    efficiently selecting elements in a stream: takeWhile and dropWhile */

    public void metodoTakeWhile() {

        // takeWhile it stops once it has found an element that fails to match
        List<Dish> slicedMenu1
                = specialMenu.stream()
                .takeWhile(dish -> dish.getCalories() < 320)
                .collect(toList());
    }

    public void metodoDropWhile() {
        // DropWhile
       /* The dropWhile operation is the complement of takeWhile.
        It throws away the elements at the start where the predicate is false.Once the predicate
        evaluates to true it stops and returns all the remaining elements,
        and it even works if there are an infinite
        number of remaining elements!*/
        List<Dish> slicedMenu2
                = specialMenu.stream()
                .dropWhile(dish -> dish.getCalories() < 320)
                .collect(toList());
    }

    // the following code skips the first two dishes that have more than 300 calories and returns the rest
    public void metodoSkip() {
        List<Dish> dishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .collect(toList());

        List<Dish> dishes1 =
                menu.stream()
                        .filter(dish -> dish.getType() == (Dish.Type.MEAT))
                        .limit(2)
                        .collect(toList());
    }

    public void metodoFlatMap() {
        String[] words = {"Goodbye", "World"};
        /*In a nutshell, the flatMap method lets you replace each value of a stream with
        another stream and then concatenates all the generated streams into a single stream.*/
        List<String> uniqueCharacters =
                Arrays.stream(words)
                        .map(word -> word.split(""))
                        .flatMap(Arrays::stream)
                        .distinct()
                        .collect(toList());
        /*Using the flatMap method has the effect of mapping each array not with a stream but
           with the contents of that stream. All the separate streams that were generated when using
            map(Arrays::stream) get amalgamated—flattened into a single stream*/

        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs =
                numbers1.stream()
                        .flatMap(i -> numbers2.stream()
                                .map(j -> new int[]{i, j})
                        )
                        .collect(toList());

        List<int[]> pairsOnlyDivisibleByThree =
                numbers1.stream()
                        .flatMap(i ->
                                numbers2.stream()
                                        .filter(j -> (i + j) % 3 == 0)
                                        .map(j -> new int[]{i, j})
                        )
                        .collect(toList());
    }

    /*
 Another common data processing idiom is finding whether some elements in a set of
data match a given property. The Streams API provides such facilities through the
allMatch, anyMatch, noneMatch, findFirst, and findAny methods of a stream.
    * */

    public void findingAndMatching(){
        //The anyMatch method returns a boolean and is therefore a terminal operation.
        //Is there an element in the stream matching the given predicate?
        if(menu.stream().anyMatch(Dish::isVegetarian)) {
           System.out.println("The menu is (somewhat) vegetarian friendly!!");
        }

        //The allMatch method works similarly to anyMatch but
      // will check to see if all the elements of the stream match the given predicate.
        boolean isHealthy = menu.stream()
                .allMatch(dish -> dish.getCalories() < 1000);

        //It ensures that no elements in the stream
        //match the given predicate.
        boolean isHealthy2 = menu.stream()
                .noneMatch(d -> d.getCalories() >= 1000);

        //The findAny method returns an arbitrary element of the current stream. It can be
        //used in conjunction with other stream operations.
        Optional<Dish> dish =
                menu.stream()
                        .filter(Dish::isVegetarian)
                        .findAny();
                       // .ifPresent(dish -> System.out.println(dish.getName());

        /*
        When to use findFirst and findAny
        You may wonder why we have both findFirst and findAny.
        The answer is parallelism. Finding the first element is more constraining in parallel.
        If you don’t care about which element is returned,
        use findAny because it’s less constraining when using parallel streams.
        * */
        List<Integer> someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> firstSquareDivisibleByThree =
                someNumbers.stream()
                        .map(n -> n * n)
                        .filter(n -> n % 3 == 0)
                        .findFirst(); // 9
    }

    //Reducers
   //An initial value, here 1.
    //A BinaryOperator<T> to combine two elements and produce a new value;
    // here you use the lambda (a, b) -> a * b
    //int product = numbers.stream().reduce(1, (a, b) -> a * b);
    //int sum = numbers.stream().reduce(0, Integer::sum);
    /*
    * There’s also an overloaded variant of reduce that doesn’t take an initial value, but it
        returns an Optional object:
        Optional<Integer> sum = numbers.stream().reduce((a, b) -> (a + b));

        * Consider the case when the stream contains no elements.
        * The reduce operation can’t return a sum because it doesn’t have an initial value.
          This is why the result is wrapped in an Optional object to indicate that the sum may be absent.

         But there’s a price to pay to execute this code in parallel, as we’ll explain later: the
         lambda passed to reduce can’t change state (for example, instance variables), and the
          operation needs to be associative and commutative so it can be executed in any order.
          int sum = numbers.parallelStream().reduce(0, Integer::sum);
    */


    //Maximum and minimum
   // Optional<Integer> max = numbers.stream().reduce(Integer::max);
    //Optional<Integer> min = numbers.stream().reduce(Integer::min);

    //cantidad de elementos en el stream
    long count = menu.stream().count();


    //Numeric streams
    /*IntStream, DoubleStream, and LongStream, which respectively specialize the elements of
    a stream to be int, long, and double—and thereby avoid hidden boxing costs. Each
    of these interfaces brings new methods to perform common numeric reductions,
    such as sum to calculate the sum of a numeric stream and max to find the maximum
    element. In addition, they have methods to convert back to a stream of objects when
    necessary. */


    /*The most common methods you’ll use to convert a stream to a specialized version are
    mapToInt, mapToDouble, and mapToLong. These methods work exactly like the method
    map that you saw earlier but return a specialized stream instead of a Stream<T>.*/

    int calories = menu.stream()
            .mapToInt(Dish::getCalories) // returns IntStream
            .sum();

    IntStream intStream = menu.stream().mapToInt(Dish::getCalories); //Converts a Stream to a numeric stream
    Stream<Integer> stream = intStream.boxed();   // Converts the numeric stream to a Stream

    //0 is not a valid max element
    OptionalInt maxCalories = menu.stream()
            .mapToInt(Dish::getCalories)
            .max();
    int max = maxCalories.orElse(1);

    //Range
    //range is exclusive, whereas rangeClosed is inclusive
    IntStream evenNumbers = IntStream.rangeClosed(1, 100)
            .filter(n -> n % 2 == 0);
    //System.out.println(evenNumbers.count());

        public void streamsFromValues(){
            Stream<String> stream = Stream.of("Modern ", "Java ", "In ", "Action");
            stream.map(String::toUpperCase).forEach(System.out::println);
        }

    //Streams from functions: creating infinite streams!
    public void streamIterate(){
        Stream.iterate(new int[]{0, 1},t -> new int[]{t[1], t[0]+t[1]})
                .limit(20)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] +")"));
        /*
        In Java 9, the iterate method was enhanced with support for a predicate.
         For example, you can generate numbers starting at 0 but stop the iteration once the number is greater than 100:
        * */
        IntStream.iterate(0, n -> n < 100, n -> n + 4)
                .forEach(System.out::println);
    }

    public void streamGenerate(){
        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);
    }

}
