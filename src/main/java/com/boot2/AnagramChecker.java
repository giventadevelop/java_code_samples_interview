package com.boot2;

import java.util.stream.Collectors;

public class AnagramChecker {
    public static boolean isAnagram(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }

        return str1.chars()
                .boxed()
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                .equals(
                        str2.chars()
                                .boxed()
                                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                );
    }

    public static void main(String[] args) {
        String word1 = "listen";
        String word2 = "silent";

        System.out.println(isAnagram(word1, word2)); // true
    }

    /**
     * This Java class AnagramChecker checks if two strings are anagrams (strings that contain the same characters with the same frequency, just in different order).
     * Class Breakdown:
     * Main Method: isAnagram(String str1, String str2)
     * Step 1: Length Check
   
     * Purpose: Quick optimization - if strings have different lengths, they can't be anagrams
     * Example: "cat" (3 chars) vs "cats" (4 chars) → immediately returns false
     * Step 2: Character Frequency Comparison
   
     * This is a functional programming approach using Java 8+ streams:
     * str1.chars() - Converts string to stream of character codes (integers)
     * .boxed() - Converts primitive int stream to Integer stream
     * .collect(Collectors.groupingBy(c -> c, Collectors.counting())) - Creates a Map<Character, Long> where:
     * Key = character
     * Value = count of that character
     * .equals() - Compares the two frequency maps
     * Example Walkthrough:
     * For str1 = "listen" and str2 = "silent":
     * str1 frequency map:
   
     * str2 frequency map:
   
     * Since both maps contain the same character counts, .equals() returns true.
     * Main Method:
   
     * Tests the method with "listen" and "silent" (which are anagrams)
     * Prints true
     * Time & Space Complexity:
     * Time: O(n) where n = length of strings
     * Space: O(k) where k = number of unique characters (typically O(1) for English alphabet)
     * Alternative Approaches:
     * Sorting: Sort both strings and compare
     * Array counting: Use a fixed-size array for character counts
     * HashSet: For case-insensitive anagrams
     * This implementation is elegant and readable but may not be the most efficient for very large strings due to object creation overhead.
     */
}
