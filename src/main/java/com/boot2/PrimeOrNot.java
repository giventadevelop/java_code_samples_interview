package com.boot2;

import java.util.Arrays;
import java.util.List;

/* A whole number greater than 1 that cannot be exactly
 divided by any whole number other than itself and 1
 (e.g. 2, 3, 5, 7, 11).
 "prime numbers are very useful in cryptography"*/
public class PrimeOrNot {

    public static void main(String[] args) {

        int inputVal = 25;

        boolean isPrime = primeNumberOrNot(inputVal);
        System.out.println(inputVal + " is prime: " + isPrime);

        // Additional test cases
        System.out.println("2 is prime: " + primeNumberOrNot(2));
        System.out.println("17 is prime: " + primeNumberOrNot(17));
        System.out.println("100 is prime: " + primeNumberOrNot(100));
    }

   private static boolean primeNumberOrNot(int number ){

       /*Math.sqrt(5) 2.2360
       Therefore, the value of root 5 is, √5 = 2.2360*/

           if (number <= 1) {
               return false;
           }
           for (int i = 2; i <= Math.sqrt(number); i++) {
               if (number % i == 0) {
                   return false;
               }
           }
           return true;
       }
   }

