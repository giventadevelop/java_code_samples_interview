package com.boot2;

import java.util.HashSet;

/**
 * This class LongestSubstringWithoutRepeating finds the length of the longest substring
 * that contains no repeating characters using the sliding window technique. It maintains a
 * HashSet to track unique characters and uses two pointers (left and right) to expand and contract
 * the window as needed - when a duplicate character is encountered, it removes characters from the left
 * until the window becomes valid again. The method returns the maximum length found during the process,
 * and in the example with "pwwkew", it correctly identifies "wke"
 * as the longest substring without repeating characters, returning a length of 3.
 */
public class LongestSubstringWithoutRepeating {
    public static int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        int left = 0;
        HashSet<Character> subStrCharset = new HashSet<>();

        for (int right = 0; right < s.length(); right++) {
            while (subStrCharset.contains(s.charAt(right))) {
                subStrCharset.remove(s.charAt(left));
                left++;
            }
            subStrCharset.add(s.charAt(right));
            maxLength = Math.max(maxLength, right - left + 1);
        }
        System.out.println("LongestSubstring: "+(subStrCharset)); // Output: 3
        return maxLength;
    }

    public static void main(String[] args) {
        String s = "pwwkew";
        System.out.println(lengthOfLongestSubstring(s)); // Output: 3
    }

    /**
     * 12.	How It Works
     * 1.	Initialize Pointers & Data Structures
     * o	left: Left boundary of the sliding window.
     * o	right: Expands the window from left to right.
     * o	set: Stores characters in the current window to track uniqueness.
     * o	maxLength: Stores the length of the longest valid substring.
     * 2.	Iterate Over the String
     * o	If s[right] is not in set, add it and expand the window.
     * o	If s[right] is already in set, remove characters from left until s[right] is unique again.
     * 3.	Update the Maximum Length
     * o	The substring length is right - left + 1, and maxLength is updated accordingly.
     * 13.	Example Execution (s = "pwwkew")
     * Step	Left (L)	Right (R)	Char	HashSet (set)	                Max Length
     * 1	  0	          0	         p	     {p}	                            1
     * 2	  0	          1	         w	    {p, w}	                            2
     * 3	  0	          2	         w	    Duplicate → Remove p, then w
     *                                       → Add w back	                    2
     * 4	  1	          3	         k	     {w, k}	                            2
     * 5	  1	          4	         e	     {w, k, e}	                        3
     * 6	  1	          5	         w	     Duplicate → Remove w, k, e,
     *                                       then w → Add w back	            3
     * The longest substring without repeating characters is "wke" or "ewk", so the output is 3.
     * 14.	Time & Space Complexity
     * •	Time Complexity: O(n)O(n)O(n)
     * o	Each character is added and removed from the set at most once, making it a linear operation.
     * •	Space Complexity: O(n)O(n)O(n)
     * o	The worst case occurs when all characters are unique, requiring O(n) extra space for the set.
     * This approach ensures efficient handling of the problem in linear time, making it optimal for large inputs. 🚀
     */
}
