package com.boot2;

import java.util.Arrays;

/**
 * Compute factorial for <em>each</em> value in an input array — highly performant batch version.
 *
 * <h2>Problem</h2>
 * Input {@code nums = {3, 5}} → output {@code {6, 120}} because {@code 3! = 6}, {@code 5! = 120}.
 *
 * <h2>Why not a nested loop per element?</h2>
 * The sketch recalculates {@code 1..n} from scratch for every {@code nums[i]}.
 * If the array is large or values repeat / share a range, that wastes work
 * (roughly {@code O(sum(nums[i]))} multiplications).
 *
 * <h2>Fast approach (used here)</h2>
 * <ol>
 *   <li>Find {@code max} in {@code nums} (one pass).</li>
 *   <li>Build {@code fact[0..max]} once: {@code fact[0]=1}, {@code fact[k]=fact[k-1]*k}.</li>
 *   <li>For each input, answer is {@code fact[nums[i]]} — O(1) lookup.</li>
 * </ol>
 * Time {@code O(n + max)}, space {@code O(max)}. Best when many queries share a bounded range.
 *
 * <h2>Bugs fixed from the sketch</h2>
 * <ul>
 *   <li>{@code lenth} → {@code length}</li>
 *   <li>Inner loop used {@code i++} instead of {@code j++} (infinite / wrong index)</li>
 *   <li>Starting {@code j} at {@code 0} multiplies by 0 and wipes the result — start at 1 or 2</li>
 *   <li>Duplicate {@code factorialInput} declaration</li>
 *   <li>Missing semicolons / incomplete {@code calculatedList} usage</li>
 * </ul>
 *
 * <p><b>Overflow:</b> {@code int} overflows at {@code 13!}. This class uses {@code long[]}
 * for results; for huge n use {@link java.math.BigInteger}.</p>
 */
public final class FactorialArray {

    private FactorialArray() {
    }

    /**
     * Returns {@code nums[i]!} for each index. Prefers one precompute up to {@code max(nums)}.
     *
     * @param nums non-negative integers (negative → {@link IllegalArgumentException})
     * @return factorials as {@code long} (safe through {@code 20!})
     */
    public static long[] factorial(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new long[0];
        }

        int max = 0;
        for (int n : nums) {
            if (n < 0) {
                throw new IllegalArgumentException("Factorial not defined for negative: " + n);
            }
            if (n > max) {
                max = n;
            }
        }

        // fact[k] = k!
        long[] fact = new long[max + 1];
        fact[0] = 1L;
        for (int k = 1; k <= max; k++) {
            fact[k] = fact[k - 1] * k;
        }

        long[] result = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            result[i] = fact[nums[i]];
        }
        return result;
    }

    /**
     * Slower baseline: recompute each factorial independently (for comparison only).
     */
    public static long[] factorialNaive(int[] nums) {
        long[] result = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            result[i] = factorialOne(nums[i]);
        }
        return result;
    }

    private static long factorialOne(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial not defined for negative: " + n);
        }
        long r = 1L;
        for (int j = 2; j <= n; j++) {
            r *= j;
        }
        return r;
    }

    public static void main(String[] args) {
        int[] nums = {3, 5};
        System.out.println("Input:  " + Arrays.toString(nums));
        System.out.println("Fast:   " + Arrays.toString(factorial(nums)));   // [6, 120]
        System.out.println("Naive:  " + Arrays.toString(factorialNaive(nums)));

        int[] withDupes = {5, 3, 5, 0, 4};
        System.out.println("Input:  " + Arrays.toString(withDupes));
        System.out.println("Fast:   " + Arrays.toString(factorial(withDupes))); // [120, 6, 120, 1, 24]
    }
}
