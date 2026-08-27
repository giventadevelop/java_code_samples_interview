package com.boot2.coderbyte;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Tree Constructor — Medium.
 *
 * <p>{@code strArr} pairs like {@code "(child,parent)"}. Return {@code "true"} if they form a
 * binary tree (each node ≤ 1 parent, each parent ≤ 2 children, exactly one root); else
 * {@code "false"}.</p>
 */
public final class TreeConstructor {

    private TreeConstructor() {
    }

    public static String TreeConstructor(String[] strArr) {
        Map<String, Integer> childCountByParent = new HashMap<>();
        Map<String, String> parentByChild = new HashMap<>();
        Set<String> nodes = new HashSet<>();

        for (String pair : strArr) {
            String cleaned = pair.replace("(", "").replace(")", "");
            String[] parts = cleaned.split(",");
            String child = parts[0].trim();
            String parent = parts[1].trim();

            nodes.add(child);
            nodes.add(parent);

            if (parentByChild.containsKey(child)) {
                return "false"; // two parents
            }
            parentByChild.put(child, parent);

            int kids = childCountByParent.merge(parent, 1, Integer::sum);
            if (kids > 2) {
                return "false"; // more than two children
            }
        }

        // Exactly one root: a node that never appears as a child
        int roots = 0;
        for (String node : nodes) {
            if (!parentByChild.containsKey(node)) {
                roots++;
            }
        }
        return roots == 1 ? "true" : "false";
    }
}
