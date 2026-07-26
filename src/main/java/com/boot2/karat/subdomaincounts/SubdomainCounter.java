package com.boot2.karat.subdomaincounts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Karat / Indeed-style subdomain aggregation.
 *
 * <p><b>Problem</b>: given a map of {@code domain -> visitCount}, return a map that includes
 * every domain <i>and every parent subdomain</i>, with counts rolled up.
 *
 * <p><b>Example (full roll-up — intended solution)</b>
 * <pre>
 * Input:
 *   google.com  -> 900
 *   a.b.c.com   -> 20
 *   b.c.com     -> 30
 *   c.com       -> 10
 *
 * Output (all suffixes get the count):
 *   a.b.c.com   -> 20
 *   b.c.com     -> 50   (= 20 from a.b.c.com + 30)
 *   c.com       -> 60   (= 20 + 30 + 10)
 *   google.com  -> 900
 *   com         -> 960  (= 900 + 20 + 30 + 10)
 * </pre>
 *
 * <p>Some write-ups only printed the original keys plus {@code com->960}. The interviewer
 * expectation for “forming sub-domains” is the full suffix roll-up above (same idea as
 * LeetCode 811 Subdomain Visit Count).
 *
 * <p><b>Critical Java gotcha</b>: {@code String.split} takes a <b>regex</b>.
 * Dot {@code "."} means “any character”. To split on a literal dot use {@code "\\."}.
 */
public class SubdomainCounter {

    /**
     * Aggregate visit counts into every subdomain suffix.
     * Uses {@link LinkedHashMap} so first-seen domain order is stable for demos;
     * for sorted output use {@link #aggregateSorted(Map)}.
     *
     * @param domainCounts domain (e.g. {@code "a.b.c.com"}) -> count
     * @return map including all suffixes with summed counts
     */
    public Map<String, Integer> aggregate(Map<String, Integer> domainCounts) {
        // LinkedHashMap: predictable iteration for interview demos (unlike HashMap).
        Map<String, Integer> result = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : domainCounts.entrySet()) {
            String domain = entry.getKey();
            int count = entry.getValue();
            if (domain == null || domain.isBlank()) {
                continue;
            }
            for (String subdomain : expandSubdomains(domain)) {
                result.merge(subdomain, count, Integer::sum);
            }
        }
        return result;
    }

    /**
     * Same aggregation, keys sorted alphabetically (TreeMap) — handy for assertions.
     */
    public Map<String, Integer> aggregateSorted(Map<String, Integer> domainCounts) {
        return new TreeMap<>(aggregate(domainCounts));
    }

    /**
     * Expand {@code "a.b.c.com"} into
     * {@code ["a.b.c.com", "b.c.com", "c.com", "com"]}.
     *
     * <p>Interview tip — wrong vs right split:
     * <pre>
     *   domain.split(".")     // WRONG — "." is regex "any char" → empties / garbage
     *   domain.split("\\.")   // RIGHT — literal dot
     * </pre>
     */
    public List<String> expandSubdomains(String domain) {
        // MUST escape the dot: split takes a regular expression.
        String[] labels = domain.split("\\.");
        List<String> subdomains = new ArrayList<>();

        // Walk from leftmost label so each suffix is a parent domain.
        // i=0 → a.b.c.com, i=1 → b.c.com, i=2 → c.com, i=3 → com
        for (int i = 0; i < labels.length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < labels.length; j++) {
                if (sb.length() > 0) {
                    sb.append('.');
                }
                sb.append(labels[j]);
            }
            subdomains.add(sb.toString());
        }
        return subdomains;
    }

    /**
     * Alternate builder: join from the right (same result, often clearer in interviews).
     */
    public List<String> expandSubdomainsFromRight(String domain) {
        String[] labels = domain.split("\\.");
        List<String> subdomains = new ArrayList<>();
        String current = "";
        for (int i = labels.length - 1; i >= 0; i--) {
            current = current.isEmpty() ? labels[i] : labels[i] + "." + current;
            subdomains.add(current);
        }
        // Built as com, c.com, b.c.com, a.b.c.com — reverse for left-to-right feel (optional).
        Collections.reverse(subdomains);
        return subdomains;
    }
}
