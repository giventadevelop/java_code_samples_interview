package com.boot2.streams;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Nth-highest salary using Java Streams — Myntra-style interview question.
 *
 * <p>Walkthrough based on:
 * <a href="https://www.youtube.com/watch?v=eBDN04LlEOg">JavaTechie — Find Nth Highest Salary</a>
 * </p>
 *
 * <h2>Problem</h2>
 * Given {@code Map&lt;employeeName, salary&gt;}, return the Nth highest salary
 * and <em>all</em> employees who earn that salary. Code must be generic for any N
 * (2nd, 3rd, 4th, …).
 *
 * <h2>Why the “sort entries then skip N-1” approach is weaker</h2>
 * Sorting {@code entrySet()} by salary descending and taking index {@code n-1}
 * picks <em>one</em> employee row. When several people share the same salary,
 * “2nd highest salary” is a salary <em>rank</em>, not the 2nd row after a flat sort.
 * Duplicate salaries make the naive answer wrong (wrong people, or the top salary
 * again if ties fill the first rows).
 *
 * <h2>Better solution</h2>
 * Group names by salary → sort distinct salaries descending → take the Nth salary
 * bucket (with its full name list).
 */
public class NthHighestSalaryDemo {

    public static void main(String[] args) {
        // Sample from the video style: unique-ish salaries
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("anil", 1000);
        map1.put("bhavna", 1300);
        map1.put("james", 1500);
        map1.put("carol", 1400);
        map1.put("tom", 1600);
        map1.put("daniel", 1700);

        // Duplicate salaries — exposes the naive bug (video's tricky case)
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("anil", 1000);
        map2.put("ankit", 1200);
        map2.put("bhavna", 1200);
        map2.put("james", 1200);
        map2.put("carol", 1100);
        map2.put("tom", 1300);
        map2.put("daniel", 1300);

        System.out.println("=== Naive (sort-by-value) — looks OK on map1 ===");
        System.out.println("2nd on map1: " + getNthHighestSalaryNaive(2, map1));

        System.out.println("\n=== Naive FAILS on map2 (duplicates) ===");
        System.out.println("2nd on map2 (WRONG — should be 1200 with ankit/bhavna/james): "
                + getNthHighestSalaryNaive(2, map2));

        System.out.println("\n=== Better (group by salary) ===");
        System.out.println("2nd on map1: " + getNthHighestSalary(2, map1));
        System.out.println("3rd on map1: " + getNthHighestSalary(3, map1));
        System.out.println("2nd on map2: " + getNthHighestSalary(2, map2));
        System.out.println("3rd on map2: " + getNthHighestSalary(3, map2));
    }

    /**
     * NAIVE approach from the first half of the video.
     *
     * <pre>
     * map.entrySet().stream()
     *    .sorted(reverseOrder(comparingByValue()))
     *    .toList()
     *    .get(n - 1);
     * </pre>
     *
     * Works only when every salary is unique. With ties, index {@code n-1} is just
     * the Nth <em>employee row</em> after sorting, not the Nth distinct salary.
     *
     * @return one map entry (name → salary), not the full set of employees for that salary
     */
    public static Map.Entry<String, Integer> getNthHighestSalaryNaive(int n, Map<String, Integer> map) {
        return map.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toList())
                .get(n - 1);
    }

    /**
     * BETTER approach (video's final solution) — correct with duplicate salaries.
     *
     * <p>Step by step:</p>
     * <ol>
     *   <li><b>groupingBy(Entry::getValue, mapping(Entry::getKey, toList()))</b>
     *       Build {@code Map&lt;salary, List&lt;names&gt;&gt;} so one salary owns every employee
     *       who earns it (e.g. 1200 → [ankit, bhavna, james]).</li>
     *   <li><b>entrySet().stream()</b> on that salary map — now each element is a
     *       distinct salary bucket.</li>
     *   <li><b>sorted(reverseOrder(comparingByKey()))</b>
     *       Sort buckets by salary descending (1700, 1600, 1500, …).</li>
     *   <li><b>toList().get(n - 1)</b>
     *       Nth highest salary rank (1-based N → 0-based index).</li>
     * </ol>
     *
     * <p>Why this is better than naive sort:</p>
     * <ul>
     *   <li>Ranks <em>salaries</em>, not employee rows.</li>
     *   <li>Returns all names tied at that salary.</li>
     *   <li>Still generic for any N.</li>
     * </ul>
     *
     * @param n   1 = highest, 2 = second highest, …
     * @param map employee name → salary
     * @return entry of (salary → list of employee names)
     */
    public static Map.Entry<Integer, List<String>> getNthHighestSalary(int n, Map<String, Integer> map) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be >= 1");
        }

        return map.entrySet().stream()
                // salary → [names...]
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())))
                .entrySet()
                .stream()
                // highest salary first
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .collect(Collectors.toList())
                .get(n - 1);
    }
}
