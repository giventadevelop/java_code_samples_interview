package com.boot2.interview;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Find values that appear more than once in a list.
 *
 * <h2>Which approach is better?</h2>
 * <table border="1" summary="Approach comparison">
 *   <tr><th>Approach</th><th>Time</th><th>Space</th><th>When to use</th></tr>
 *   <tr>
 *     <td>{@link #findDuplicatesWithHashMap(List)}</td>
 *     <td>O(n)</td>
 *     <td>O(u) unique keys</td>
 *     <td><b>Best default in interviews / production</b> — clear, fast, no hidden scans.</td>
 *   </tr>
 *   <tr>
 *     <td>{@link #findDuplicatesWithGroupingBy(List)}</td>
 *     <td>O(n)</td>
 *     <td>O(u)</td>
 *     <td>Same Big-O as HashMap; prefer when you want concise stream-style code.</td>
 *   </tr>
 *   <tr>
 *     <td>{@link #findDuplicatesWithFrequency(List)}</td>
 *     <td>O(n²)</td>
 *     <td>O(u) for the result set</td>
 *     <td>Only for tiny lists / demos. Avoid on large inputs.</td>
 *   </tr>
 * </table>
 *
 * <p><b>Winner:</b> HashMap (or groupingBy). Both count each element once.
 * The frequency approach looks short but rescans the list for every element.</p>
 *
 * <p>Example input {@code ["abc","ss","abc","ss","hh"]} → {@code {"abc","ss"}}.</p>
 */
public class StringDuplicates {

    /**
     * <h3>HashMap counting — preferred imperative solution</h3>
     *
     * <p><b>Why this is better than {@code Collections.frequency}:</b></p>
     * <ul>
     *   <li>One forward pass builds {@code value → count}.</li>
     *   <li>Second short pass over the map (at most unique-key size) keeps counts &gt; 1.</li>
     *   <li>Time {@code O(n)}; space {@code O(u)} for unique strings.</li>
     *   <li>Easy to explain in an interview: “count, then filter.”</li>
     * </ul>
     *
     * <p><b>vs groupingBy:</b> Same complexity. HashMap loops are often easier to debug
     * and slightly less allocation overhead than stream pipelines. Prefer this when
     * clarity and control matter more than one-liner style.</p>
     *
     * <p>{@code merge(s, 1, Integer::sum)} increments the count for {@code s}
     * (inserts 1 if absent).</p>
     *
     * @param values list that may contain duplicates (null elements not expected)
     * @return set of values that occur more than once (order not guaranteed)
     */
    public static Set<String> findDuplicatesWithHashMap(List<String> values) {
        Map<String, Integer> counts = new HashMap<>();
        for (String s : values) {
            counts.merge(s, 1, Integer::sum);
        }

        Set<String> duplicates = new HashSet<>();
        for (Map.Entry<String, Integer> e : counts.entrySet()) {
            if (e.getValue() > 1) {
                duplicates.add(e.getKey());
            }
        }
        return duplicates;
    }

    /**
     * <h3>Stream {@code groupingBy} — preferred functional solution</h3>
     *
     * <p><b>Why this is still “better” than frequency:</b> {@code groupingBy} also does
     * a single O(n) aggregation into a frequency map, then filters entries with
     * count &gt; 1. No nested full-list scans.</p>
     *
     * <p><b>vs HashMap loops:</b> Same Big-O and same idea (build counts → filter).
     * Choose this for idiomatic Java 8+ stream style and shorter call sites.
     * Choose HashMap if you want explicit steps or to avoid stream overhead.</p>
     *
     * <p><b>Not “better” than HashMap on asymptotic complexity</b> — they are peers.
     * Pick based on team style.</p>
     *
     * @param values list that may contain duplicates
     * @return set of values that occur more than once
     */
    public static Set<String> findDuplicatesWithGroupingBy(List<String> values) {
        return values.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    /**
     * <h3>Original {@code Collections.frequency} approach — correct but not scalable</h3>
     *
     * <p>For each element {@code s}, {@code Collections.frequency(values, s)} walks the
     * <em>entire</em> list to count matches. Doing that inside {@code filter} for every
     * element → roughly {@code n * n} comparisons → <b>O(n²)</b>.</p>
     *
     * <p><b>Why it looks attractive:</b> very little code; easy to write in a hurry.</p>
     *
     * <p><b>Why it is worse:</b></p>
     * <ul>
     *   <li>Repeated work: the same list is scanned over and over.</li>
     *   <li>On large lists (10k–1M+) it becomes noticeably slow.</li>
     *   <li>Interviewers often follow up: “What’s the complexity?” — answer O(n²).</li>
     * </ul>
     *
     * <p><b>Verdict:</b> fine for tiny demos only. Prefer
     * {@link #findDuplicatesWithHashMap(List)} or
     * {@link #findDuplicatesWithGroupingBy(List)}.</p>
     *
     * @param values list that may contain duplicates
     * @return set of values that occur more than once
     */
    public static Set<String> findDuplicatesWithFrequency(List<String> values) {
        return values.stream()
                .filter(s -> Collections.frequency(values, s) > 1)
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        List<String> duplStrs = Arrays.asList("abc", "ss", "abc", "ss", "hh");

        System.out.println("HashMap     : " + findDuplicatesWithHashMap(duplStrs));
        System.out.println("groupingBy  : " + findDuplicatesWithGroupingBy(duplStrs));
        System.out.println("frequency   : " + findDuplicatesWithFrequency(duplStrs));
        // all print: [abc, ss]  (order may vary for HashSet)
    }
}
