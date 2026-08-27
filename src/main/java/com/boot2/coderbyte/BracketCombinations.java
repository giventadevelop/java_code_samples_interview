package com.boot2.coderbyte;

/**
 * Bracket Combinations — Hard.
 *
 * <p>Return how many valid combinations of {@code num} pairs of parentheses exist
 * (the {@code num}-th Catalan number).</p>
 *
 * <p>Examples: {@code 3 → 5}  {@code (()()), (())(), ()(()), ()()(), ((()))}.</p>
 */
public final class BracketCombinations {

    private BracketCombinations() {
    }

    public static int BracketCombinations(int num) {
        if (num < 0) {
            return 0;
        }
        // Catalan(n) via DP: C[0]=1, C[i] = sum C[j]*C[i-1-j]
        long[] catalan = new long[num + 1];
        catalan[0] = 1;
        for (int i = 1; i <= num; i++) {
            long sum = 0;
            for (int j = 0; j < i; j++) {
                sum += catalan[j] * catalan[i - 1 - j];
            }
            catalan[i] = sum;
        }
        return (int) catalan[num];
    }
}
