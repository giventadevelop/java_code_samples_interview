package com.boot2.coderbyte;

/**
 * Longest Word — Easy.
 *
 * <p>Return the first longest word in {@code sen}. Ignore punctuation; digits may be part of a word
 * (e.g. {@code "Hello world123 567" → "world123"}).</p>
 */
public final class LongestWord {

    private LongestWord() {
    }

    public static String LongestWord(String sen) {
        String[] words = sen.split("[^a-zA-Z0-9]+");
        String longest = "";
        for (String word : words) {
            if (word.length() > longest.length()) {
                longest = word;
            }
        }
        return longest;
    }
}
