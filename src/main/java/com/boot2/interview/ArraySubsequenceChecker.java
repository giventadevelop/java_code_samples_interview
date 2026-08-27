package com.boot2.interview;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Interview question: is {@code array2} a subsequence of {@code array1}?
 *
 * <p>A subsequence keeps the same relative order as in the first array; elements
 * need not be adjacent.</p>
 *
 * <p>Run: {@code java -cp target/classes com.boot2.interview.ArraySubsequenceChecker}</p>
 *
 * <pre>{@code
 * array1 = [5,1,22,25,6,-1,8,10]
 * array2 = [1,6,-1,10]  → true
 * array2 = [1,6,10,-1]  → false  ( -1 appears after 10 in array2, but before 10 in array1)
 * }</pre>
 */
public class ArraySubsequenceChecker {

    /**
     * Broken interview sketch (does not compile / does not solve the problem).
     *
     * <h3>Problems in the original attempt</h3>
     * <ul>
     *   <li>{@code Array.asList} → should be {@code Arrays.asList}.</li>
     *   <li>Raw {@code List} types; missing braces around the for-loop body.</li>
     *   <li>{@code previousVal} logic never compares positions in array1.</li>
     *   <li>{@code if (array1)} is invalid (List is not a boolean).</li>
     *   <li>{@code indexOf} is O(n) and wrong for duplicates / order checks.</li>
     * </ul>
     *
     * <h3>How we fix it</h3>
     * Use two pointers: walk array1 once; advance the array2 pointer whenever the current
     * elements match. If we consume all of array2, it is a subsequence (same order, not
     * necessarily adjacent).
     *
     * <pre>{@code
     * array1 = [5,1,22,25,6,-1,8,10]
     * array2 = [1,6,-1,10]  → true
     * array2 = [1,6,10,-1]  → false  ( -1 appears after 10 in array2, but before 10 in array1)
     * }</pre>
     *
     * <h3>Original broken sketch (for study only)</h3>
     * <pre>{@code
     * List<Integer> list1 = Array.asList(array1);   // typo: Arrays.asList
     * List<Integer> list2 = Array.asList(array2);
     *
     * public boolean checkArraySequence(List array1, List array2) {
     *     boolean isSequence = false;
     *     for (Integer ar1 : array2)
     *       int previousVal = ar1;
     *       if (array2.indexOf(ar1) == 0) {
     *         previousVal = array2.get(0);
     *         continue;
     *       }
     *       if (array1) {   // invalid — List is not a boolean
     *       }
     * }
     * }</pre>
     */
    public static boolean isSubsequence(List<Integer> array1, List<Integer> array2) {
        if (array2 == null || array2.isEmpty()) {
            return true;
        }
        if (array1 == null || array1.isEmpty()) {
            return false;
        }

        int j = 0; // pointer into array2
        for (int i = 0; i < array1.size() && j < array2.size(); i++) {
            if (Objects.equals(array1.get(i), array2.get(j))) {
                j++;
            }
        }
        return j == array2.size();
    }

    /** Overload for primitive arrays used in the interview examples. */
    public static boolean isSubsequence(int[] array1, int[] array2) {
        return isSubsequence(
                Arrays.stream(array1).boxed().toList(),
                Arrays.stream(array2).boxed().toList());
    }

    /**
     * Runs the interview examples:
     * <ul>
     *   <li>{@code [1,6,-1,10]} is a subsequence of {@code [5,1,22,25,6,-1,8,10]} → true</li>
     *   <li>{@code [1,6,10,-1]} is not (order of {@code 10} and {@code -1} is wrong) → false</li>
     * </ul>
     */
    public static void demoSubsequence() {
        System.out.println("=== Array subsequence check ===");

        int[] array1 = {5, 1, 22, 25, 6, -1, 8, 10};
        int[] array2True = {1, 6, -1, 10};
        int[] array2False = {1, 6, 10, -1};

        // Prefer Arrays.asList (not Array.asList) when building Lists from arrays of Integer
        List<Integer> list1 = Arrays.stream(array1).boxed().toList();
        List<Integer> list2True = Arrays.stream(array2True).boxed().toList();
        List<Integer> list2False = Arrays.stream(array2False).boxed().toList();

        System.out.println(list2True + " is subsequence of " + list1 + " ? "
                + isSubsequence(list1, list2True));
        System.out.println(list2False + " is subsequence of " + list1 + " ? "
                + isSubsequence(list1, list2False));
    }

    public static void main(String[] args) {
        demoSubsequence();
    }
}
