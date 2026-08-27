package com.boot2.coderbyte;

/**
 * First Factorial — Easy.
 *
 * <p>Return {@code num!} (e.g. {@code 4 → 24}).</p>
 */
public final class FirstFactorial {

    private FirstFactorial() {
    }

    public static long FirstFactorial(int num) {
        long result = 1;
        for (int i = 2; i <= num; i++) {
            result *= i;
        }
        return result;
    }
}
