package com.boot2;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class JavaTest {

    public static void main(String[] args){

        String characterTest = "new";

        Map<Character, Long> charCount =  characterTest.chars().mapToObj(x->(char)x).collect(Collectors.groupingBy(x->x,Collectors.counting()) );

        System.out.println(charCount );

        int[] arr = new int[]{1, 2, 3};
      int sumArray=  Arrays.stream(arr).sum();
        System.out.println("sumArray = "+sumArray );
    }

}
