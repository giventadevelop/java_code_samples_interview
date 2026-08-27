package com.boot2.coderbyte;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Bracket Matcher — Medium.
 *
 * <p>Only {@code '('} and {@code ')'} count. Return {@code "1"} if correctly matched,
 * otherwise {@code "0"}.</p>
 *
 * <p>Examples: {@code "(hello (world))" → "1"}, {@code "((hello (world))" → "0"}.</p>
 */
public final class BracketMatcher {

    private BracketMatcher() {
    }

    public static String BracketMatcher(String str) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return "0";
                }
                stack.pop();
            }
        }
        return stack.isEmpty() ? "1" : "0";
    }
}
