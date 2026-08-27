package com.boot2;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a string {@code s}, find the length of the longest substring without duplicate characters.
 *
 * <p>Correct approach: <b>sliding window</b> with a map of each character's last seen index.
 * Expand {@code right}; when {@code s[right]} was already inside the window, jump {@code left}
 * past its previous index. Track {@code max(right - left + 1)}.</p>
 *
 * <pre>
 * Example 1: "abcabcbb" → 3  ("abc")
 * Example 2: "bbbbb"    → 1  ("b")
 * Example 3: "pwwkew"   → 3  ("wke")  — must be a substring, not "pwke"
 * </pre>
 *
 * <p>Time O(n), space O(min(n, charset)).</p>
 */
public class LongestSubString {

    /**
     * Returns the length of the longest substring of {@code s} with all unique characters.
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        // char → last index where it appeared
        Map<Character, Integer> lastIndex = new HashMap<>();
        int maxLength = 0;
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // Duplicate inside the current window [left, right] → move left past it
            if (lastIndex.containsKey(c) && lastIndex.get(c) >= left) {
                left = lastIndex.get(c) + 1;
            }

            lastIndex.put(c, right);
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(lengthOfLongestSubstring("bbbbb"));    // 1
        System.out.println(lengthOfLongestSubstring("pwwkew"));   // 3
    }
}
