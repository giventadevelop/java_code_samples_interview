package com.boot2;

import java.util.Stack;

public class ClosingBrackets {

    public static boolean areBracketsMatched(String input) {
        Stack<Character> stack = new Stack<>();

        for (char c : input.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) {
                    return false;
                }
                char opening = stack.pop();
                if (!isMatchingPair(opening, c)) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    private static boolean isMatchingPair(char opening, char closing) {
        return (opening == '(' && closing == ')') ||
                (opening == '[' && closing == ']') ||
                (opening == '{' && closing == '}');
    }

    public static void main(String[] args) {
        String[] testCases = {"{[}]", "{}[]", "{[]}", "([)]", "((()))", "{[()]}"};

        for (String test : testCases) {
            boolean result = areBracketsMatched(test);
            System.out.println("Input: " + test + " - Brackets matched: " + result);
        }
    }
}
