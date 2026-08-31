package com.boot2;

/**
 * Brute-force longest substring without repeating characters using
 * {@link StringBuilder}, matching the approach in
 * <a href="https://www.youtube.com/watch?v=GS9TyovoU4c&amp;t=7s">this video</a>:
 *
 * <ul>
 *   <li>Outer loop: each index {@code i} is a possible start of a substring.</li>
 *   <li>Inner loop: grow a {@code currentSubstring} StringBuilder with each
 *       following character {@code s.charAt(j)}.</li>
 *   <li>Before appending, if {@code currentSubstring.indexOf(...)} is not
 *       {@code -1}, the character already appears → {@code break} the inner loop.</li>
 *   <li>Otherwise {@code append}, then update max length / best substring when longer.</li>
 * </ul>
 *
 * <p>Time O(n²) because of the nested loops (and {@code indexOf} scans the
 * window). For the video’s faster HashMap / sliding-window solution, see
 * {@link LongestSubString}.</p>
 *
 * <pre>
 * Example: "abcabcbb" → length 3, e.g. "abc"
 * Example: "bbbbb"    → length 1, "b"
 * Example: "pwwkew"   → length 3, "wke"
 * </pre>
 */
public class LongestSubStringStringBuilder {

    /**
     * Video-style StringBuilder solution: nested loops + indexOf + append + break.
     * Returns only the length.
     */
    public static int lengthOfLongestSubstring(String s) {
        return longestSubstring(s).length();
    }

    /**
     * Same algorithm; returns one longest substring without repeating characters
     * (the first one found when several share the same max length).
     */
    public static String longestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        String best = "";

        for (int i = 0; i < s.length(); i++) {
            StringBuilder currentSubstring = new StringBuilder();

            for (int j = i; j < s.length(); j++) {
                if (currentSubstring.indexOf(String.valueOf(s.charAt(j))) != -1) {
                    break;
                }

                currentSubstring.append(s.charAt(j));
                if (currentSubstring.length() > best.length()) {
                    best = currentSubstring.toString();
                }
            }
        }

        return best;
    }

    public static void main(String[] args) {
        printResult("abcabcbb");
        printResult("bbbbb");
        printResult("pwwkew");
    }

    private static void printResult(String input) {
        String longest = longestSubstring(input);
        System.out.println("Input: \"" + input + "\" → longest=\"" + longest
                + "\", length=" + longest.length());
    }
}
