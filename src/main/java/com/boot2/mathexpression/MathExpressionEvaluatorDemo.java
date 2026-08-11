package com.boot2.mathexpression;

public class MathExpressionEvaluatorDemo {

    public static void main(String[] args) {
        MathExpressionEvaluator evaluator = new MathExpressionEvaluator();

        String[] expressions = {
                "3 + 4 * 2",
                "(3 + 4) * 2",
                "10 - 2 / 4",
                "((2 + 3) * (4 - 1)) / 5",
                "12.5 + 3.5 * 2",
                "-5 + 10 * 2",
                "100 / (2 + 3) * 4"
        };

        for (String expression : expressions) {
            double result = evaluator.evaluate(expression);
            System.out.println(expression + " = " + result);
        }
    }
}
