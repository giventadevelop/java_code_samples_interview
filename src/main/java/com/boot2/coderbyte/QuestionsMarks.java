package com.boot2.coderbyte;

/**
 * Questions Marks — Easy.
 *
 * <p>Return {@code "true"} if every pair of digits that sum to 10 has exactly three
 * {@code '?'} characters between them; otherwise {@code "false"}. Also {@code "false"}
 * when no such digit pair exists.</p>
 */
public final class QuestionsMarks {

    private QuestionsMarks() {
    }

    public static String QuestionsMarks(String str) {
        boolean foundPair = false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                continue;
            }
            int left = str.charAt(i) - '0';
            int qCount = 0;
            for (int j = i + 1; j < str.length(); j++) {
                char c = str.charAt(j);
                if (c == '?') {
                    qCount++;
                } else if (Character.isDigit(c)) {
                    int right = c - '0';
                    if (left + right == 10) {
                        foundPair = true;
                        if (qCount != 3) {
                            return "false";
                        }
                    }
                    break; // only the next digit after left matters for the "between" check
                }
            }
        }
        return foundPair ? "true" : "false";
    }
}
