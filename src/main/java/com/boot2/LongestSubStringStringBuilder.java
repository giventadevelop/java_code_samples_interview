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
 *   <li>Otherwise {@code append}, then update {@code maxLength} with
 *       {@code Math.max}.</li>
 * </ul>
 *
 * <p>Time O(n²) because of the nested loops (and {@code indexOf} scans the
 * window). For the video’s faster HashMap / sliding-window solution, see
 * {@link LongestSubString}.</p>
 *
 * <pre>
 * Example: "abcabcbb" → 3
 * Example: "bbbbb"    → 1
 * Example: "pwwkew"   → 3
 * </pre>
 */
public class LongestSubStringStringBuilder {

    /**
     * Video-style StringBuilder solution: nested loops + indexOf + append + break.
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int maxLength = 0;

        // Outer loop: each character as the start of a candidate substring
        for (int i = 0; i < s.length(); i++) {
            StringBuilder currentSubstring = new StringBuilder();

            // Inner loop: add each subsequent character until a repeat is found
            for (int j = i; j < s.length(); j++) {
                // StringBuilder has no contains() — indexOf != -1 means already present
                if (currentSubstring.indexOf(String.valueOf(s.charAt(j))) != -1) {
                    break;
                }

                currentSubstring.append(s.charAt(j));
                maxLength = Math.max(maxLength, currentSubstring.length());
            }
        }

        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(lengthOfLongestSubstring("bbbbb"));    // 1
        System.out.println(lengthOfLongestSubstring("pwwkew"));   // 3
    }
}
