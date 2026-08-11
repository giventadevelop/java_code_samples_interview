package com.boot2.mathexpression;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Evaluates a math expression string and returns a {@code double}.
 * Supports {@code + - * /}, parentheses, decimals, whitespace, and unary minus.
 * Precedence: {@code * /} before {@code + -}; parentheses override precedence.
 */
public class MathExpressionEvaluator {

    public double evaluate(String expression) {
        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("Expression must not be blank");
        }

        Deque<Double> values = new ArrayDeque<>();
        Deque<Character> operators = new ArrayDeque<>();

        int i = 0;
        int n = expression.length();
        boolean expectOperand = true;

        while (i < n) {
            char ch = expression.charAt(i);

            if (Character.isWhitespace(ch)) {
                i++;
                continue;
            }

            if (ch == '(') {
                operators.push(ch);
                expectOperand = true;
                i++;
                continue;
            }

            if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    applyTopOperator(values, operators);
                }
                if (operators.isEmpty() || operators.pop() != '(') {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
                expectOperand = false;
                i++;
                continue;
            }

            if (isOperator(ch)) {
                if (expectOperand && ch == '-') {
                    i = parseNumber(expression, i, values, true);
                    expectOperand = false;
                    continue;
                }
                if (expectOperand) {
                    throw new IllegalArgumentException("Unexpected operator at index " + i + ": " + ch);
                }
                while (!operators.isEmpty()
                        && operators.peek() != '('
                        && precedence(operators.peek()) >= precedence(ch)) {
                    applyTopOperator(values, operators);
                }
                operators.push(ch);
                expectOperand = true;
                i++;
                continue;
            }

            if (Character.isDigit(ch) || ch == '.') {
                i = parseNumber(expression, i, values, false);
                expectOperand = false;
                continue;
            }

            throw new IllegalArgumentException("Invalid character at index " + i + ": " + ch);
        }

        while (!operators.isEmpty()) {
            if (operators.peek() == '(') {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            applyTopOperator(values, operators);
        }

        if (values.size() != 1) {
            throw new IllegalArgumentException("Invalid expression: " + expression);
        }
        return values.pop();
    }

    private int parseNumber(String expression, int start, Deque<Double> values, boolean unaryMinus) {
        int i = start;
        if (unaryMinus) {
            i++;
        }

        int numberStart = i;
        boolean sawDot = false;
        while (i < expression.length()) {
            char ch = expression.charAt(i);
            if (Character.isDigit(ch)) {
                i++;
            } else if (ch == '.' && !sawDot) {
                sawDot = true;
                i++;
            } else {
                break;
            }
        }

        if (numberStart == i) {
            throw new IllegalArgumentException("Expected number at index " + start);
        }

        double value = Double.parseDouble(expression.substring(numberStart, i));
        values.push(unaryMinus ? -value : value);
        return i;
    }

    private void applyTopOperator(Deque<Double> values, Deque<Character> operators) {
        if (values.size() < 2) {
            throw new IllegalArgumentException("Not enough operands for operator");
        }
        char op = operators.pop();
        double right = values.pop();
        double left = values.pop();
        values.push(apply(left, right, op));
    }

    private double apply(double left, double right, char op) {
        return switch (op) {
            case '+' -> left + right;
            case '-' -> left - right;
            case '*' -> left * right;
            case '/' -> {
                if (right == 0.0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield left / right;
            }
            default -> throw new IllegalArgumentException("Unknown operator: " + op);
        };
    }

    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private int precedence(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> 0;
        };
    }
}
