package com.boot2;

import java.util.Arrays;
import java.util.List;

/** A whole number greater than 1 that cannot be exactly
 divided by any whole number other than itself and 1
 (e.g. 2, 3, 5, 7, 11).
 "prime numbers are very useful in cryptography"

 Divisibility: Prime numbers are only divisible by 1 and the number itself.
 Example: The number 7 is prime because it can only be divided evenly by 1 and 7.
 Composite Numbers: Numbers that have more than two factors are composite (e.g., 4, 6, 8, 9, 10).
 1 is Special: The number 1 is neither prime nor composite, it has only one factor (itself).
 https://www.youtube.com/watch?v=5gBtzdM5M5k

 */
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

