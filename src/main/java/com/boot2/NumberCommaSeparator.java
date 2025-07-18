package com.boot2;

public class NumberCommaSeparator {
    public static String addCommas(int number) {
        String numStr = Integer.toString(number);
        StringBuilder result = new StringBuilder();

        int len = numStr.length();
        int count = 0;

        for (int i = len - 1; i >= 0; i--) {
            result.insert(0, numStr.charAt(i));
            count++;

            // Insert a comma every three digits (except at the start)
            if (count % 3 == 0 && i != 0) {
                result.insert(0, ",");
            }
        }
        return result.toString();
    }

    /**

     * *** Explanation *****
     * This class NumberCommaSeparator formats integers by adding commas as thousand separators.
     * It converts a number like 1234567 to 1,234,567 for better readability.
     * The method works by iterating through the digits from right to left and inserting a comma every
     * three digits, except at the beginning of the number.
     * Convert the Integer to a String: Integer.toString(number) converts the number to a string.
     * Loop Through the String from Right to Left:
     * Use a StringBuilder to build the formatted string efficiently.
     * Start from the last digit and insert it at the beginning.
     * Keep a count to track every third digit.
     * Insert a Comma Every Three Digits:
     * If count % 3 == 0, insert a comma before continuing.
     * Ensure the comma is not added at the start (i != 0 check).
     * Why This Works?
     * It doesn't rely on any external formatting libraries.
     * Simple and efficient string manipulation using StringBuilder.
     * Works for any positive integer!
     * explain this result.insert(0, numStr.charAt(i));
     * Great question! Let's break it down:
     * Understanding result.insert(0, numStr.charAt(i));
     * This line is part of the method where we're formatting a number with comma separators. Here's how it works:
     * numStr.charAt(i)
     * numStr is the string representation of the number (e.g., "12456").
     * charAt(i) extracts the character at index i as we loop from right to left.
     * result.insert(0, ...)
     * result is a StringBuilder that stores the formatted number.
     * .insert(0, ...) adds the extracted character at index 0, meaning at the very beginning of result.
     * Effect in Action
     * Suppose we process "12456":
     * Initially, result is empty.
     * Iteration 1: Inserts '6' → result = "6"
     * Iteration 2: Inserts '5' at the beginning → result = "56"
     * Iteration 3: Inserts '4' at the beginning → result = "456"
     * And so on...
     * This technique ensures characters are added in reverse order, simulating left-to-right insertion while processing right to left.
     * @param args
     */
    public static void main(String[] args) {
        int number = 12456;
        System.out.println(addCommas(number)); // Output: 12,456
    }
}
