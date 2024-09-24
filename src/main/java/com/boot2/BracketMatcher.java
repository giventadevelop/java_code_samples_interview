package com.boot2;

import java.util.Stack;

public class BracketMatcher {
    public static boolean areBracketsMatched(String str) {
        Stack<Character> stack = new Stack<>();
        
        for (char ch : str.toCharArray()) {
            // If the character is an opening bracket, push it onto the stack.
            if (ch == '{' || ch == '[' || ch == '(') {
                stack.push(ch);
            }
            // If the character is a closing bracket, check if it matches the top of the stack.
            else if (ch == '}' || ch == ']' || ch == ')') {
                // If the stack is empty or the brackets don't match, return false.
                if (stack.isEmpty() || !isMatchingPair(stack.pop(), ch)) {
                    return false;
                }
            }
        }
        // If the stack is empty, all brackets matched; otherwise, they didn't.
        return stack.isEmpty();
    }

    private static boolean isMatchingPair(char open, char close) {
        return (open == '{' && close == '}') || 
               (open == '[' && close == ']') || 
               (open == '(' && close == ')');
    }

    public static void main(String[] args) {
//        String input = "{{][}}";
        String input = "{[}]";
        boolean result = areBracketsMatched(input);
        
        System.out.println("Are brackets matched? " + result);
    }
}
