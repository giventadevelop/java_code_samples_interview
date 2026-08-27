package com.boot2.coderbyte;

/**
 * Min Window Substring — Medium.
 *
 * <p>{@code strArr[0]} = N (haystack), {@code strArr[1]} = K (required chars, with multiplicity).
 * Return the smallest substring of N that contains all characters in K. If several are the same
 * length, return the first.</p>
 *
 * <p>Example: {@code ["aaabaaddae", "aed"] → "dae"}.</p>
 */
public final class MinWindowSubstring {

    private MinWindowSubstring() {
    }

    public static String MinWindowSubstring(String[] strArr) {
        String n = strArr[0];
        String k = strArr[1];

        int[] need = new int[256];
        int required = 0;
        for (int i = 0; i < k.length(); i++) {
            if (need[k.charAt(i)]++ == 0) {
                required++;
            }
        }

        int[] window = new int[256];
        int formed = 0;
        int bestStart = 0;
        int bestLen = Integer.MAX_VALUE;
        int left = 0;

        for (int right = 0; right < n.length(); right++) {
            char rc = n.charAt(right);
            window[rc]++;
            if (need[rc] > 0 && window[rc] == need[rc]) {
                formed++;
            }

            while (formed == required && left <= right) {
                int len = right - left + 1;
                if (len < bestLen) {
                    bestLen = len;
                    bestStart = left;
                }
                char lc = n.charAt(left);
                window[lc]--;
                if (need[lc] > 0 && window[lc] < need[lc]) {
                    formed--;
                }
                left++;
            }
        }

        return bestLen == Integer.MAX_VALUE ? "" : n.substring(bestStart, bestStart + bestLen);
    }
}
