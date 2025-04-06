package com.boot2;

import org.apache.tomcat.util.digester.Rules;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*Rules of the FizzBuzz Game
The rules of the FizzBuzz game are very simple.

Say Fizz if the number is divisible by 3.
Say Buzz if the number is divisible by 5.
Say FizzBuzz if the number is divisible by both 3 and 5.
Return the number itself, if the number is not divisible by 3 and 5.*/
public class NumbersDivisibleFizzBuzz {

    public static String processFizzBuzzStream(IntStream intStream) {

        IntFunction<String> buzzFn = (x) -> {

            if (x % 3 == 0 && x % 5 == 0) {
                return "FizzBuzz";
            } else if (x % 3 == 0) {
                return "Fizz";
            } else if (x % 5 == 0) {
                return "Buzz";
            }
            return Integer.toString(x);
        };
        String processStream = intStream.mapToObj(x -> buzzFn.apply(x))
                .collect(Collectors.joining(" , "));
        System.out.println("\n processStream  " + processStream);
        return processStream;
    }

    /**
     * to print from input data from console
     */
    public static void fizzBuzz(int n) {

        if (n % 3 == 0 && n % 5 == 0) {
            System.out.println("FizzBuzz");
            ;
        } else if (n % 3 == 0) {
            System.out.println("Fizz");
        } else if (n % 5 == 0) {
            System.out.println("Buzz");
        } else {
            System.out.println(n);
        }

    }

    public static void fizzBuzzForARange(int n) {

        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
    }

    public static void main(String args[]) throws java.io.IOException {

        // int c = checkExceptionsFinally(4,0) ;
        // System.out.println(c);

        /*
         * The selected code demonstrates the usage of
         * three different mathematical functions from the
         * Java Math class: floor(), ceil(), and round().
         * These functions are being applied to the same decimal
         * number (-4.7) to show how they behave differently.
         * Let's break it down:
         * 1.
         * Math.floor(-4.7):
         * The floor() function returns the largest integer
         * less than or equal to the given number.
         * For -4.7, the floor value is -5.0.
         * Output: -5.0
         * 2.
         * Math.ceil(-4.7):
         * The ceil() function returns the smallest integer
         * greater than or equal to the given number.
         * For -4.7, the ceiling value is -4.0.
         * Output: -4.0
         * 3.
         * Math.round(-4.7):
         * The round() function returns the closest integer
         * to the given number.
         * For negative numbers, it rounds towards
         * zero if the fraction is less than 0.5,
         * and away from zero if the fraction is 0.5
         * or greater.
         * For -4.7, the rounded value is -5.
         * Output: -5
         * Each of these function calls is wrapped in a
         * System.out.println() statement, which prints the
         * result to the console. The output is also labeled
         * with a descriptive string (e.g., "Math Floor") to
         * clearly identify which function is being demonstrated.
         * This code snippet is useful for understanding how
         * these mathematical functions behave with negative
         * decimal numbers, which can sometimes be
         * counterintuitive. It's a good example to include
         * in interview preparation materials, as it
         * tests knowledge of basic Java Math functions
         * and their behavior with negative numbers.
         */
        System.out.println("Math Floor");
        System.out.println(Math.floor(-4.7));
        System.out.println("Math Ceil");
        System.out.println(Math.ceil(-4.7));
        System.out.println("Math Round");
        System.out.println(Math.round(-4.7));
        // System.out.println("Math Min");
        // System.out.println(Math.min(-4.7));
        // String result =
        // NumbersDivisibleFizzBuzz.processFizzBuzzStream(IntStream.of(5,6,7,8));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        /*
         * for (int i = 0; i < 4; i++) {
         * System.out.println("Enter the number");
         * int n = Integer.parseInt(bufferedReader.readLine().trim());
         * NumbersDivisibleFizzBuzz.fizzBuzz(n);
         * }
         */

        System.out.println("Enter the number");
        int n = Integer.parseInt(bufferedReader.readLine().trim());
        NumbersDivisibleFizzBuzz.fizzBuzzForARange(n);

        bufferedReader.close();
    }

    static int checkExceptionsFinally(int a, int b) {
        int c = -1;

        try {
            c = a / b;

        } catch (Exception e) {
            System.err.println("Exc");
        } finally {
            System.err.println("Final");
        }

        return c;
    }
}
