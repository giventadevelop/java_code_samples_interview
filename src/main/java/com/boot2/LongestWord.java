package com.boot2;

/**
 * Longest Word — find the first longest word in a sentence.
 *
 * <h2>Two ways to split into words</h2>
 * <ul>
 *   <li>{@code split("\\s+")} — split on whitespace only. Correct when the sentence has
 *       <em>no punctuation stuck to words</em> (spaces only). Wrong for
 *       {@code "fun&!! time"} because {@code "fun&!!"} stays one token.</li>
 *   <li>{@code split("[^a-zA-Z0-9]+")} — split on anything that is not a letter/digit.
 *       Correct for Coderbyte-style rules: ignore punctuation; digits may count in a word
 *       ({@code "world123"}).</li>
 * </ul>
 *
 * <pre>
 * "Hello world123 567"     → "world123"  (both split styles agree on this one)
 * "fun&!! time"            → "time"      (needs punctuation-aware split)
 * </pre>
 */
public final class LongestWord {

    private LongestWord() {
    }

    /**
     * Coderbyte-style: ignore punctuation; keep letters and digits as part of a word.
     */
    public static String LongestWord(String sen) {
        return longestWordPunctuationAware(sen);
    }

    /**
     * Split on one-or-more whitespace: {@code "\\s+"}.
     * Use only when words are already clean (no attached punctuation).
     */
    public static String longestWordWhitespaceSplit(String sen) {
        if (sen == null || sen.isBlank()) {
            return "";
        }
        String[] words = sen.trim().split("\\s+");
        String longest = "";
        for (String word : words) {
            if (word.length() > longest.length()) {
                longest = word;
            }
        }
        return longest;
    }

    /**
     * Split on non-alphanumeric runs: {@code "[^a-zA-Z0-9]+"}.
     * Preferred when punctuation may appear (Coderbyte Longest Word).
     */
    public static String longestWordPunctuationAware(String sen) {
        if (sen == null || sen.isBlank()) {
            return "";
        }
        String[] words = sen.split("[^a-zA-Z0-9]+");
        String longest = "";
        for (String word : words) {
            if (word.isEmpty()) {
                continue; // leading/trailing split artifacts
            }
            if (word.length() > longest.length()) {
                longest = word;
            }
        }
        return longest;
    }

    public static void main(String[] args) {
        // Two sample sentences
        String sentence1 = "Hello world123 567";          // digits in a “word”
        String sentence2 = "The quick brown fox jumps!"; // punctuation at end

        System.out.println("=== Sentence 1: \"" + sentence1 + "\" ===");
        System.out.println("  split(\"\\\\s+\")              → "
                + longestWordWhitespaceSplit(sentence1));
        System.out.println("  split(\"[^a-zA-Z0-9]+\")     → "
                + longestWordPunctuationAware(sentence1));
        System.out.println("  LongestWord (default)        → " + LongestWord(sentence1));

        System.out.println();
        System.out.println("=== Sentence 2: \"" + sentence2 + "\" ===");
        System.out.println("  split(\"\\\\s+\")              → "
                + longestWordWhitespaceSplit(sentence2));
        System.out.println("  split(\"[^a-zA-Z0-9]+\")     → "
                + longestWordPunctuationAware(sentence2));
        System.out.println("  LongestWord (default)        → " + LongestWord(sentence2));
        // Note: "\\s+" keeps "jumps!" as one token; punctuation-aware yields "jumps" / "brown"/"quick" (length 5).

        System.out.println();
        System.out.println("=== When \\\\s+ is NOT enough: \"fun&!! time\" ===");
        String tricky = "fun&!! time";
        System.out.println("  split(\"\\\\s+\")              → "
                + longestWordWhitespaceSplit(tricky)
                + "  (WRONG for Coderbyte)");
        System.out.println("  split(\"[^a-zA-Z0-9]+\")     → "
                + longestWordPunctuationAware(tricky)
                + "  (CORRECT)");
    }
}
