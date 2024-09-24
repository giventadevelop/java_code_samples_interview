package com.boot2;

import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class NumbersDivisibleBy3And5 {
    public static void main(String args[]) {

        IntStream intStreamStr = IntStream.range(1,10);
        IntStream intStreamSum = IntStream.range(1,10);
        getDivisible3And5SumTotal(intStreamStr, intStreamSum);
    }

    public static String getDivisible3And5SumTotal
               ( IntStream intStreamStr, IntStream intStreamSum) {

        String processStream = intStreamStr.filter(i->i % 3 == 0 || i % 5 == 0)
                              .mapToObj(Integer::toString)
                              .collect(Collectors.joining(" + "));
        System.out.println("\n processStream  "+ processStream);
        int sumTotal = intStreamSum.filter(i->i % 3 == 0 || i % 5 == 0).sum() ;
        processStream = processStream+ " = "+ sumTotal;
        System.out.println("\n sumTotal  "+ " = "+ sumTotal);
        System.out.println("\n processStream  "+ " = "+ processStream);
        return processStream;
    }
}
