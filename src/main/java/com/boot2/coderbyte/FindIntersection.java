package com.boot2.coderbyte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Find Intersection — Easy.
 *
 * <p>{@code strArr} has two comma-separated sorted number lists. Return their intersection as a
 * comma-separated string (no spaces), or {@code "false"} if empty.</p>
 *
 * <p>Example: {@code ["1, 3, 4, 7, 13", "1, 2, 4, 13, 15"] → "1,4,13"}.</p>
 */
public final class FindIntersection {

    private FindIntersection() {
    }

    public static String FindIntersection(String[] strArr) {
        Set<String> first = Arrays.stream(strArr[0].split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(HashSet::new));

        List<String> intersection = new ArrayList<>();
        for (String token : strArr[1].split(",")) {
            String n = token.trim();
            if (first.contains(n)) {
                intersection.add(n);
            }
        }

        if (intersection.isEmpty()) {
            return "false";
        }
        return String.join(",", intersection);
    }
}
