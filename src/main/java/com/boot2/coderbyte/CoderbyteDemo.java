package com.boot2.coderbyte;

import com.boot2.LongestWord;

/**
 * Runs sample inputs for each Coderbyte Java challenge in this package.
 *
 * <pre>
 * mvn -q -DskipTests compile
 * java -cp target/classes com.boot2.coderbyte.CoderbyteDemo
 * </pre>
 */
public final class CoderbyteDemo {

    private CoderbyteDemo() {
    }

    public static void main(String[] args) {
        System.out.println("=== Easy ===");
        System.out.println("FirstFactorial(4) = " + FirstFactorial.FirstFactorial(4));
        System.out.println("FirstReverse(\"coderbyte\") = " + FirstReverse.FirstReverse("coderbyte"));
        System.out.println("LongestWord(\"Hello world123 567\") = "
                + LongestWord.LongestWord("Hello world123 567"));
        System.out.println("CodelandUsernameValidation(\"aa_\") = "
                + CodelandUsernameValidation.CodelandUsernameValidation("aa_"));
        System.out.println("CodelandUsernameValidation(\"u__hello_world123\") = "
                + CodelandUsernameValidation.CodelandUsernameValidation("u__hello_world123"));
        System.out.println("FindIntersection = "
                + FindIntersection.FindIntersection(new String[]{"1, 3, 4, 7, 13", "1, 2, 4, 13, 15"}));
        System.out.println("QuestionsMarks(\"arrb6???4xxbl5???eee5\") = "
                + QuestionsMarks.QuestionsMarks("arrb6???4xxbl5???eee5"));
        System.out.println("QuestionsMarks(\"aa6?9\") = " + QuestionsMarks.QuestionsMarks("aa6?9"));

        System.out.println("\n=== Medium ===");
        System.out.println("BracketMatcher(\"(hello (world))\") = "
                + BracketMatcher.BracketMatcher("(hello (world))"));
        System.out.println("BracketMatcher(\"((hello (world))\") = "
                + BracketMatcher.BracketMatcher("((hello (world))"));
        System.out.println("TreeConstructor = "
                + TreeConstructor.TreeConstructor(
                        new String[]{"(1,2)", "(2,4)", "(5,7)", "(7,2)", "(9,5)"}));
        System.out.println("TreeConstructor(invalid) = "
                + TreeConstructor.TreeConstructor(new String[]{"(1,2)", "(3,2)", "(2,12)", "(5,2)"}));
        System.out.println("MinWindowSubstring([aaabaaddae, aed]) = "
                + MinWindowSubstring.MinWindowSubstring(new String[]{"aaabaaddae", "aed"}));

        System.out.println("\n=== Hard ===");
        System.out.println("BracketCombinations(3) = " + BracketCombinations.BracketCombinations(3));
        System.out.println("BracketCombinations(4) = " + BracketCombinations.BracketCombinations(4));
    }
}
