package com.boot2.karat.subdomaincounts;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Runnable demo for the Karat / Indeed subdomain-count question.
 *
 * <p>Run:
 * {@code mvn -q -DskipTests compile}
 * {@code java -cp target/classes com.boot2.karat.subdomaincounts.SubdomainCountDemo}
 *
 * <p>Reference discussion:
 * https://leetcode.com/discuss/interview-experience/1713353/karat-interview-experience-indeed
 */
public class SubdomainCountDemo {

    public static void main(String[] args) {
        System.out.println("=== Karat / Indeed: Subdomain Count Aggregation ===");
        System.out.println();

        demonstrateSplitGotcha();

        Map<String, Integer> input = sampleInput();
        System.out.println("--- Input (domain -> count) ---");
        input.forEach((d, c) -> System.out.println("  " + d + " -> " + c));
        System.out.println();

        SubdomainCounter counter = new SubdomainCounter();

        System.out.println("--- Subdomain expansion for a.b.c.com ---");
        List<String> parts = counter.expandSubdomains("a.b.c.com");
        System.out.println("  " + parts);
//        parts.forEach(x -> System.out.print("list frech "+x + " "));
        System.out.println();

        Map<String, Integer> aggregated = counter.aggregateSorted(input);
        System.out.println("--- Aggregated output (full suffix roll-up) ---");
        aggregated.forEach((d, c) -> System.out.println("  " + d + " -> " + c));
        System.out.println();

        System.out.println("--- Expected ---");
        System.out.println("  a.b.c.com  -> 20");
        System.out.println("  b.c.com    -> 50   (20 + 30)");
        System.out.println("  c.com      -> 60   (20 + 30 + 10)");
        System.out.println("  com        -> 960  (900 + 20 + 30 + 10)");
        System.out.println("  google.com -> 900");
        System.out.println();
        System.out.println("Note: some forum posts only listed originals + com->960;");
        System.out.println("full Karat intent is rolling the count into EVERY parent subdomain.");
        System.out.println();

        assertExpectations(aggregated);
        System.out.println("Demo assertions passed.");
    }

    static Map<String, Integer> sampleInput() {
        // LinkedHashMap keeps the example order from the interview write-up.
        Map<String, Integer> input = new LinkedHashMap<>();
        input.put("google.com", 900);
        input.put("a.b.c.com", 20);
        input.put("b.c.com", 30);
        input.put("c.com", 10);
        return input;
    }

    /**
     * Shows why {@code split(".")} fails and {@code split("\\.")} works —
     * the exact tip the Indeed Karat interviewer gave.
     */
    private static void demonstrateSplitGotcha() {
        System.out.println("--- Java gotcha: String.split is REGEX ---");
        String domain = "a.b.c.com";

        String[] wrong = domain.split(".");
        System.out.println("  split(\".\")     length=" + wrong.length
                + "  (broken — '.' means any character)");

        String[] right = domain.split("\\.");
        System.out.println("  split(\"\\\\.\")   parts=" + String.join(" | ", right)
                + "  (correct — literal dot)");
        System.out.println();
    }

    private static void assertExpectations(Map<String, Integer> aggregated) {
        expect(aggregated, "google.com", 900);
        expect(aggregated, "a.b.c.com", 20);
        expect(aggregated, "b.c.com", 50);
        expect(aggregated, "c.com", 60);
        expect(aggregated, "com", 960);

        if (aggregated.size() != 5) {
            throw new IllegalStateException("Expected 5 keys, got " + aggregated.size());
        }
    }

    private static void expect(Map<String, Integer> map, String key, int value) {
        Integer actual = map.get(key);
        if (actual == null || actual != value) {
            throw new IllegalStateException(
                    "Expected " + key + " -> " + value + " but was " + actual);
        }
    }
}
