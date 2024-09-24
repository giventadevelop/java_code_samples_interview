package com.boot2;

import java.util.Arrays;
import java.util.List;

public class CheckWarehouseStock {

    public static boolean isEvenInteger(int number){
        System.out.println("isEvenInteger for "+ number +" = "+ number % 2);
        return number % 2 == 0;
    }

    public static boolean isEvenDecimalNumber(double number){

        System.out.println("isEvenDecimalNumber for "+ number +" = "+ number % 2);
        return number % 2 == 0;
    }
    public static boolean isEvenNumber(Number number){

        if (number instanceof Integer){

            System.out.println("isEvenInteger for "+ number +" = "+ number.intValue() % 2);
            return number.intValue() % 2 == 0;
//            return isEvenInteger(number.intValue());
        }else if(number instanceof Double){

            System.out.println("isEvenDecimalNumber for "+ number +" = "+ number.doubleValue() % 2);
            return number.doubleValue() % 2 == 0;
//            return isEvenDecimalNumber(number.doubleValue());
        }
        return false;
    }
    public static void main(String[] args) {

        System.out.println("check number 10 even  "+ CheckWarehouseStock.isEvenNumber(10));
        System.out.println("check number 22.00 even  "+ CheckWarehouseStock.isEvenNumber(22.00));
        System.out.println("check number 9 even  "+ CheckWarehouseStock.isEvenNumber(9));
        System.out.println("check number 21.00 even  "+ CheckWarehouseStock.isEvenNumber(21.00));
        System.out.println("check number 21.20 even  "+ CheckWarehouseStock.isEvenNumber(22.20));

        String test = "test,test,test,ssdsds,dssdsd";
        String [] strArray = test.split(",");
        List<String> strList = Arrays.asList(strArray);

        long countStr = strList.stream().filter(x-> x.equalsIgnoreCase("test")).count();

        System.out.println("The occurences of string is : "+ countStr);

        // invoke add method with two parameters
        int sum = add(10, 21);
        System.out.println("The sum is: " + sum);


        



    }

    // method to add two integers
    public static int add(int a, int b) {
        // loop thru a list of strings
        String [] strArray = {"test","test","test","ssdsds","dssdsd"};
        List<String> strList = Arrays.asList(strArray);
        // convert list to stream
        strList.stream().filter(x-> x.equalsIgnoreCase("test")).count();
        // convert list to stream
        long count = strList.stream().filter(x-> x.equalsIgnoreCase("test")).count();
        System.out.println("The occurences of string is : "+ count);

        return a + b;
   }





}
