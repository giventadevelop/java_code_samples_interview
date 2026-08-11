package com.boot2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountOfWords {

    /*Arrays.asList(...) does not return a java.util.ArrayList.

    It returns a List whose runtime type is a fixed-size private nested class (Arrays.ArrayList), which is not the same as java.util.ArrayList.

            So this works (assignment to the interface):

    List<String> charList = Arrays.asList(charArray);
    But this fails (assignment to a concrete subclass that is not returned):

    ArrayList<String> charList2 = Arrays.asList(charArray); // compile error
    Because a List is not guaranteed to be an ArrayList.

    If you need a real ArrayList:


    ArrayList<String> charList2 = new ArrayList<>(Arrays.asList(charArray));
    That copies into a resizable java.util.ArrayList (so add/remove work).*/
    public static void main(String[] args) {

        String inStr = "Hello World";
        String[] charArray =  inStr.split(" ");
        List<String> charList = Arrays.asList(charArray);
        //ArrayList<String> charList2 = Arrays.asList(charArray);
        // Explanation on why the above line gives compiler error is given in class documentation about
        System.out.println("Count of words in charArray is :"+ charArray.length);
        System.out.println("Count of words in charArray is :"+ Arrays.stream(charArray).count());

        System.out.println("Count of words in charList is :"+ charList.size());
//        System.out.println("Count of words in charList is :"+ (long) charList.size());
        System.out.println("Count of words in charList is without (long) : "+  charList.size());
//        charList.stream().distinct().collect(Collectors.toMap(c->c, Collections.frequency(charList,c)));

//        charList.stream().distinct().collect(Collectors.toMap();

//        after 20 % discount bike cost is 160  x * (20/100) = y
    }



}
