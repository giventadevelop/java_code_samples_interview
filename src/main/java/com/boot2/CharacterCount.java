package com.boot2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Character-frequency demo. The broken {@code Arrays.asList(toCharArray())} line
 * is kept (commented) with an explanation of why it does not compile.
 */
public class CharacterCount {

    public static void main(String[] args) {

        String charString = args.length > 0 ? args[0] : "hello";

        /*
         * WHY THIS DOES NOT COMPILE:
         *
         *   List<Character> charList = Arrays.asList(charString.toCharArray());
         *
         * 1) String.toCharArray() returns a primitive array: char[]
         *
         * 2) Arrays.asList(T... a) is a generic varargs method. It does NOT
         *    "unwrap" a char[] into Character elements. A primitive array is
         *    ONE object, so the compiler treats the call as:
         *
         *      Arrays.asList( oneElementOfType_char[] )
         *
         *    and the return type becomes List<char[]>, NOT List<Character>.
         *
         * 3) You cannot assign List<char[]> to List<Character> → compile error:
         *    "incompatible types: List<char[]> cannot be converted to List<Character>"
         *
         * Contrast with a Character[] / String[]:
         *   Arrays.asList('a', 'b')           → List<Character>  (OK, autoboxed)
         *   Arrays.asList(new String[]{"a"})  → List<String>     (OK)
         *   Arrays.asList(new char[]{'a'})    → List<char[]>     (NOT List<Character>)
         *
         * NOTE: Even if this compiled, Arrays.asList(...) returns a fixed-size
         * list backed by the array — charList.add(ch) would throw
         * UnsupportedOperationException at runtime.
         *
         * ------------------------------------------------------------
         * BETTER OPTIONS than a manual for-loop into ArrayList:
         *
         * Option A — one-liner: count directly from the String (no List).
         * Prefer this when you only need the frequency map.
         *
         *   Map<Character, Long> charMap = charString.chars()
         *           .mapToObj(c -> (char) c)
         *           .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
         *
         * Option B — one-liner List<Character> (Java 16+, unmodifiable):
         *
         *   List<Character> charList = charString.chars()
         *           .mapToObj(c -> (char) c)
         *           .toList();
         *
         * Option C — one-liner resizable ArrayList:
         *
         *   List<Character> charList = charString.chars()
         *           .mapToObj(c -> (char) c)
         *           .collect(Collectors.toCollection(ArrayList::new));
         *
         * Option D — classic loop (below). Clear, but more verbose.
         * chars() is an IntStream of UTF-16 code units; mapToObj boxes each to Character.
         */

        // Option D (active): build a resizable List<Character> with a loop
        List<Character> charList = new ArrayList<>();
        for (char ch : charString.toCharArray()) {
            // enhanced-for on char[] is fine: each char is autoboxed to Character
            charList.add(ch);
        }

        // Option A (alternative — uncomment to use instead of Option D + collect below):
        // Map<Character, Long> charMap = charString.chars()
        //         .mapToObj(c -> (char) c)
        //         .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        // Option B (alternative list):
        // List<Character> charList = charString.chars().mapToObj(c -> (char) c).toList();

        // Option C (alternative resizable list):
        // List<Character> charList = charString.chars()
        //         .mapToObj(c -> (char) c)
        //         .collect(Collectors.toCollection(ArrayList::new));

        /*
         * WHY THIS DOES NOT COMPILE:
         *
         *   Map<Object, Integer> charMap = charList.stream()
         *           .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
         *
         * The collect(...) expression has type Map<Character, Long>, but you declared
         * Map<Object, Integer>. Two mismatches:
         *
         * 1) VALUE type — Integer vs Long
         *    Collectors.counting() is defined to return Long (not Integer).
         *    Signature idea: Collector<T, ?, Long>
         *    So the map is Map<K, Long>. Integer is wrong.
         *
         * 2) KEY type — Object vs Character (generics are invariant)
         *    classifier e -> e on Stream<Character> makes K = Character.
         *    Even Map<Character, Long> cannot be assigned to Map<Object, Long>
         *    because in Java Map is invariant: Map<Character, ?> is not a subtype
         *    of Map<Object, ?>. (A Map<Object, Long> would allow put(new Object(), 1L),
         *    which would break a Map that only holds Character keys.)
         *
         * Fixes that compile:
         *   Map<Character, Long> charMap = ...counting();
         *   Map<?, Long> charMap = ...counting();          // wildcard OK for reading
         *   var charMap = ...counting();                   // compiler infers Character, Long
         *
         * If you truly need Integer counts:
         *   .collect(Collectors.groupingBy(e -> e, Collectors.summingInt(e -> 1)));
         *   → Map<Character, Integer>
         */

        Map<Character, Long> charMap = charList.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        System.out.println("char count " + charMap);
    }
}
