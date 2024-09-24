package com.boot2;

import java.util.*;

public class IteratorTest {

    public static void main(String[] args) {

       /* List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));

        for (Iterator<String> iter = list.iterator(); iter.hasNext(); ) {
            if (iter.next().equals("b")) {
                 iter.remove();    // #1
                // list.remove("b"); // #2
            }
        }

        System.out.println("List "+ list);*/


        int [] sumNumbers = {1,2,3,4,5};
        int [] indexes = {0,0};
         indexes = findIndexes(sumNumbers,11);
         if(indexes !=null) {
             System.out.println("indexes are = " + indexes[0] + " , " + indexes[1]);
         }else{
             System.out.println("No Matching numbers");
         }

    }

    /*

    Find indexes of two numbers in an array that add up to a given number

    For example if the array is {1,2,3,4,5} and the number is 7, the indexes are 2 and 4

    */
    public static int[] findIndexes(int[] arr,int number) {

        int [] sumNumbers = {0,1};

        for (int i = 0; i < arr.length; i++) {

            for (int j = 0; j < arr.length; j++) {

                if (arr[i]+ arr[j]==number) {
                    sumNumbers[0]= i;
                    sumNumbers[1]= j;
                    return sumNumbers;
                    } ;
                }
            }

        return null;


    }

    //write a function that takes a string and returns true if the brackets are balanced


    public Boolean balanced(String inStr, char charLeftbracketType, char charRightbracketType, String strbracketType) {

        List beacketList = Arrays.asList(strbracketType);

        HashMap<String,Integer>  bracketMapper = new HashMap();

         if(inStr.contains(strbracketType)){

         }else {
             return false;
         }

         int leftBracketCount = 0;
         int rightBracketCount = 0;

        for (int i = 0; i < inStr.length(); i++) {

            if(inStr.charAt(i)== charLeftbracketType){
                leftBracketCount++;
            }

        }

        for (int i = 0; i < inStr.length(); i++) {

            if(inStr.charAt(i)== charRightbracketType){
                rightBracketCount++;
            }

        }

        return null;


    }



}
