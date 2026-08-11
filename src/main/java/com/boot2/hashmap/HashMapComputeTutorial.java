package com.boot2.hashmap;

import java.util.HashMap;
import java.util.Map;

/**
 * Tutorial demo for {@link HashMap#compute(Object, java.util.function.BiFunction)}.
 *
 * <pre>
 * map.compute(key, (k, currentValue) -&gt; newValue);
 * </pre>
 *
 * Behavior summary:
 * <ul>
 *   <li>Key exists, function returns value  → replace with new value</li>
 *   <li>Key exists, function returns null   → remove the key</li>
 *   <li>Key missing, function returns value → insert key/value</li>
 *   <li>Key missing, function returns null  → map unchanged</li>
 * </ul>
 *
 * <h2>Helpful guideline: {@code compute()} vs {@code merge()}</h2>
 *
 * In a Java {@link HashMap}, {@code compute()} recomputes a value for a specific key
 * from scratch using its current value, while {@code merge()} combines an explicitly
 * provided new value with an existing value using a remapping function.
 *
 * <pre>
 * map.compute(key, (k, currentValue) -&gt; recomputedValue);
 * map.merge(key, newValue, (oldValue, incomingValue) -&gt; combinedValue);
 * </pre>
 *
 * <table border="1" cellpadding="4" cellspacing="0" summary="compute vs merge">
 *   <caption>Core differences</caption>
 *   <tr>
 *     <th>Feature</th>
 *     <th>{@code compute(K key, BiFunction remappingFunction)}</th>
 *     <th>{@code merge(K key, V value, BiFunction remappingFunction)}</th>
 *   </tr>
 *   <tr>
 *     <td>Primary use case</td>
 *     <td>Updating a value based only on the current key/value</td>
 *     <td>Combining a new external value with an existing value</td>
 *   </tr>
 *   <tr>
 *     <td>New value needed?</td>
 *     <td>No. It only takes the key and the function</td>
 *     <td>Yes. You must pass an explicit default/new value</td>
 *   </tr>
 *   <tr>
 *     <td>If key is absent</td>
 *     <td>Runs the function with {@code null} as the current value</td>
 *     <td>Directly inserts the new value without running the function</td>
 *   </tr>
 *   <tr>
 *     <td>If key is present</td>
 *     <td>Runs the function using the key and current value</td>
 *     <td>Runs the function using the old value and the new value</td>
 *   </tr>
 *   <tr>
 *     <td>If function returns {@code null}</td>
 *     <td>Removes the key from the map</td>
 *     <td>Removes the key from the map</td>
 *   </tr>
 * </table>
 *
 * Rule of thumb:
 * <ul>
 *   <li>Use {@code compute()} when the new value depends only on what is already in the map.</li>
 *   <li>Use {@code merge()} when you already have an incoming value to combine with the map.</li>
 * </ul>
 */
public class HashMapComputeTutorial {

    public static void main(String[] args) {
        section("1. Update an existing value");
        updateExistingValue();

        section("2. Handle a missing key safely (null check)");
        handleMissingKeySafely();

        section("3. Remove a key by returning null");
        removeKeyByReturningNull();

        section("4. Missing key + function returns null -> no change");
        missingKeyReturnsNullNoChange();

        section("5. Behavior matrix walkthrough");
        behaviorMatrix();

        section("6. Alternative: computeIfAbsent()");
        computeIfAbsentDemo();

        section("7. Alternative: computeIfPresent()");
        computeIfPresentDemo();

        section("8. Practical example - word / item counts");
        practicalWordCount();
    }

    /**
     * Key exists and function returns a new value → old value is replaced.
     */
    static void updateExistingValue() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Shoes", 50);

        // Double the price of Shoes
        map.compute("Shoes", (key, value) -> value * 2);

        System.out.println("After doubling Shoes: " + map);
        // Output: {Shoes=100}
    }

    /**
     * Key may be missing — guard against null to avoid NullPointerException.
     */
    static void handleMissingKeySafely() {
        HashMap<String, Integer> map = new HashMap<>();

        // Safely increment or initialize a count
        map.compute("Apples", (key, value) -> (value == null) ? 1 : value + 1);
        System.out.println("First compute (missing key): " + map);
        // Output: {Apples=1}

        map.compute("Apples", (key, value) -> (value == null) ? 1 : value + 1);
        System.out.println("Second compute (existing key): " + map);
        // Output: {Apples=2}
    }

    /**
     * Key exists and function returns null → key is removed from the map.
     */
    static void removeKeyByReturningNull() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Oranges", 10);

        map.compute("Oranges", (key, value) -> null);

        System.out.println("After returning null: " + map);
        // Output: {}
    }

    /**
     * Key is missing and function returns null → map stays unchanged.
     */
    static void missingKeyReturnsNullNoChange() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Bananas", 5);

        Integer result = map.compute("Mangoes", (key, value) -> null);

        System.out.println("Returned from compute: " + result);
        System.out.println("Map unchanged: " + map);
        // Output: Returned from compute: null
        //         Map unchanged: {Bananas=5}
    }

    /**
     * Prints all four core outcomes side by side.
     */
    static void behaviorMatrix() {
        System.out.println(String.format("%-40s | %-30s | %s",
                "Starting situation", "Function result", "Map after compute"));
        System.out.println("-".repeat(100));

        // 1) Key exists, function returns value
        HashMap<String, Integer> m1 = new HashMap<>();
        m1.put("A", 10);
        m1.compute("A", (k, v) -> v + 5);
        System.out.println(String.format("%-40s | %-30s | %s",
                "Key exists (A=10)", "returns 15", m1));

        // 2) Key exists, function returns null
        HashMap<String, Integer> m2 = new HashMap<>();
        m2.put("A", 10);
        m2.compute("A", (k, v) -> null);
        System.out.println(String.format("%-40s | %-30s | %s",
                "Key exists (A=10)", "returns null", m2));

        // 3) Key missing, function returns value
        HashMap<String, Integer> m3 = new HashMap<>();
        m3.compute("A", (k, v) -> 7);
        System.out.println(String.format("%-40s | %-30s | %s",
                "Key missing", "returns 7", m3));

        // 4) Key missing, function returns null
        HashMap<String, Integer> m4 = new HashMap<>();
        m4.compute("A", (k, v) -> null);
        System.out.println(String.format("%-40s | %-30s | %s",
                "Key missing", "returns null", m4.isEmpty() ? "{}" : m4));
    }

    /**
     * {@code computeIfAbsent} runs only when the key is missing (or mapped to null).
     */
    static void computeIfAbsentDemo() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Tea", 2);

        // Key missing → function runs, value is inserted
        map.computeIfAbsent("Coffee", key -> 1);
        System.out.println("After computeIfAbsent(Coffee): " + map);

        // Key already present → function is NOT run; value stays 2
        map.computeIfAbsent("Tea", key -> {
            System.out.println("This lambda should not run for Tea");
            return 99;
        });
        System.out.println("After computeIfAbsent(Tea) - still 2: " + map);
    }

    /**
     * {@code computeIfPresent} runs only when the key exists and maps to a non-null value.
     */
    static void computeIfPresentDemo() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Milk", 3);

        // Key present → function runs
        map.computeIfPresent("Milk", (key, value) -> value + 1);
        System.out.println("After computeIfPresent(Milk): " + map);

        // Key missing → function does NOT run; map unchanged
        map.computeIfPresent("Juice", (key, value) -> {
            System.out.println("This lambda should not run for Juice");
            return 1;
        });
        System.out.println("After computeIfPresent(Juice) - no insert: " + map);

        // Returning null from computeIfPresent removes the key
        map.computeIfPresent("Milk", (key, value) -> null);
        System.out.println("After computeIfPresent(Milk -> null): " + map);
    }

    /**
     * Real-world style use: tally item counts with one compute call per item.
     */
    static void practicalWordCount() {
        String[] items = {"apple", "banana", "apple", "orange", "banana", "apple"};
        Map<String, Integer> counts = new HashMap<>();

        for (String item : items) {
            counts.compute(item, (key, value) -> (value == null) ? 1 : value + 1);
        }

        System.out.println("Item counts: " + counts);
        // Output: {banana=2, orange=1, apple=3}  (order may vary)
    }

    private static void section(String title) {
        System.out.println();
        System.out.println("==== " + title + " ====");
    }
}
