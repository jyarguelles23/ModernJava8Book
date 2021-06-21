package com.viqsystems.Streams;

import com.viqsystems.Clases.Trader.Trader;
import com.viqsystems.Clases.Transaction.Transaction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.joining;

public class StreamExcersises {
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
   // 1 Find all transactions in the year 2011 and sort them by value (small to high).
            public void transactions2011(){
                List<Transaction> result= transactions.stream()
                        .filter(transaction -> transaction.getYear()== 2011)
                        .sorted(comparing(Transaction::getValue))
                        .collect(Collectors.toList());
            }


     //  2 What are all the unique cities where the traders work?
     public void uniqueCities(){
         List<String> result= transactions.stream()
                 .map(transaction -> transaction.getTrader().getCity())
                 .distinct()
                 .collect(Collectors.toList());
     }

       //     3 Find all traders from Cambridge and sort them by name.
       public void Cambridge(){
           List<Trader> result= transactions.stream()
                   .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                   .map(transaction -> transaction.getTrader())
                   .distinct()
                   .sorted(comparing(Trader::getName))
                   .collect(Collectors.toList());
       }
         //   4 Return a string of all traders’ names sorted alphabetically.
         public void tradersSorted() {
             String result  = transactions.stream()
                     .map(transaction -> transaction.getTrader().getName())
                     .distinct()
                     .sorted()
                    // .reduce("",(n1,n2) -> n1+n2); ineficiente
                    .collect(joining());
         }

    //5 Are any traders based in Milan?
    public void traderMilan (){
        boolean result  = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
    }
      // 6 Print the values of all transactions from the traders living in Cambridge.
       public void transactionCambridge(){
           transactions.stream()
                   .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                   .map(Transaction::getValue)
                   .forEach(System.out::println);
       }

        //    7 What’s the highest value of all the transactions?
          public void highestValue(){
              Optional<Integer> value=transactions.stream()
                      .map(Transaction::getValue)
                      .reduce(Integer::max);
          }
          //  8 Find the transaction with the smallest value.
          public void smallestValue(){
              Optional<Transaction> value=transactions.stream()
                                           .min(comparing(Transaction::getValue));
          }
}
