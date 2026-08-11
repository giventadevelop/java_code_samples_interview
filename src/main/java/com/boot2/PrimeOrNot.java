package com.boot2;

/**
 * Checks whether an integer is a prime number.
 *
 * <h2>Definition of a prime number</h2>
 * A prime number is a whole number greater than 1 that has exactly two distinct
 * positive divisors: {@code 1} and itself. Examples: 2, 3, 5, 7, 11, 13, 17.
 * <ul>
 *   <li>Primes are only divisible by 1 and the number itself (no remainder).</li>
 *   <li>Composite numbers have more than two factors (e.g. 4, 6, 8, 9, 10, 25).</li>
 *   <li>{@code 1} is neither prime nor composite (it has only one factor: itself).</li>
 *   <li>{@code 2} is the only even prime; every larger even number is divisible by 2.</li>
 * </ul>
 * Prime numbers are widely used in cryptography (e.g. RSA key generation).
 *
 * <h2>Why this algorithm uses {@code Math.sqrt(number)}</h2>
 * To test whether {@code n} is prime, we look for a divisor of {@code n}.
 * If {@code n} has a factor pair {@code (a, b)} such that {@code a * b = n},
 * then one of those factors is always {@code <= sqrt(n)} and the other is
 * {@code >= sqrt(n)}.
 * <p>
 * Example: {@code n = 25}, {@code sqrt(25) = 5}. Factor pairs are
 * {@code (1, 25)} and {@code (5, 5)}. Any factor larger than 5 already has a
 * matching smaller factor we would have found earlier.
 * </p>
 * So it is enough to check divisors from {@code 2} through {@code floor(sqrt(n))}.
 * That filters out most of the search space and cuts the work from roughly
 * {@code O(n)} down to roughly {@code O(sqrt(n))}.
 *
 * <p>Reference overview: https://www.youtube.com/watch?v=5gBtzdM5M5k</p>
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

    /**
     * Returns {@code true} if {@code number} is prime; otherwise {@code false}.
     *
     * <p>Filtering / trial-division steps:</p>
     * <ol>
     *   <li>Reject {@code number <= 1} immediately (not prime by definition).</li>
     *   <li>Loop candidate divisors {@code i} from 2 up to {@code Math.sqrt(number)}.</li>
     *   <li>If {@code number % i == 0}, {@code i} divides {@code number} evenly,
     *       so {@code number} is composite → return {@code false}.</li>
     *   <li>If no divisor is found in that range, {@code number} is prime.</li>
     * </ol>
     *
     * Why stop at {@code Math.sqrt(number)}?
     * Factors come in pairs. For example, with 100 the pairs are
     * (1,100), (2,50), (4,25), (5,20), (10,10). Once you pass {@code sqrt(100) = 10},
     * every remaining factor already had a partner checked earlier. Checking beyond
     * the square root would only repeat work and would not find a new first factor.
     *
     * @param number the integer to test
     * @return whether {@code number} is prime
     */
    private static boolean primeNumberOrNot(int number) {
        // 1 is not prime; negatives / 0 are also not prime.
        if (number <= 1) {
            return false;
        }

        // Only need to test divisors up to sqrt(number).
        // Example: Math.sqrt(25) == 5.0, so the loop checks i = 2, 3, 4, 5.
        for (int i = 2; i <= Math.sqrt(number); i++) {
            // Even division means we found a factor other than 1 / itself.
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
