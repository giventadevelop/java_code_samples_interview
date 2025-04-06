package com.boot2;

import java.util.Arrays;
import java.util.List;

public class CountOfWords {

    public static void main(String[] args) {

        String inStr = "Hello World";
        String[] charArray =  inStr.split(" ");
        List<String> charList = Arrays.asList(charArray);
        System.out.println("Count of words in charArray is :"+ charArray.length);
        System.out.println("Count of words in charArray is :"+ Arrays.stream(charArray).count());

        System.out.println("Count of words in charList is :"+ charList.size());
        System.out.println("Count of words in charList is :"+ (long) charList.size());
//        charList.stream().distinct().collect(Collectors.toMap(c->c, Collections.frequency(charList,c)));

//        charList.stream().distinct().collect(Collectors.toMap();

//        after 20 % discount bike cost is 160  x * (20/100) = y
    }



}
