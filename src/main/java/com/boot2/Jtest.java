package com.boot2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Jtest {


    public static void main(String[] args) {
        System.out.println("Hello World JTest");

        Integer[] intArray = {1,1, 2,2,2 , 3,3,3,3} ;
        List<Integer> numList = Arrays.asList(intArray);

        Map<Integer, Long> countMap = numList.stream()
                          .collect(Collectors.groupingBy(e ->e, Collectors.counting()));

        System.out.println("fCount of Items : "+ countMap  );

       /*MultiplierIntfc multiplyItems = (int a, int b ) -> a * b;
       int multResult =  multiplyItems.multiplyItems(2,3);
       System.out.println("funtional Interface multiplyItems : "+ multResult  );*/
    }

}
