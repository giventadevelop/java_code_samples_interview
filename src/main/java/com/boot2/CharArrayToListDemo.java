package com.boot2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * How to build a {@link List}{@code Character} from a character array or from
 * every character in a word/sentence.
 *
 * <h2>Important details</h2>
 * <ul>
 *   <li>{@code Arrays.asList(charArray)} needs {@code Character[]} (boxed), not
 *       primitive {@code char[]} — a {@code char[]} is treated as <em>one</em> element
 *       (the array object), not as individual letters.</li>
 *   <li>{@code Arrays.asList(...)} returns a fixed-size list backed by the array.
 *       Wrap with {@code new ArrayList<>(...)} if you need {@code add}/{@code remove}.</li>
 * </ul>
 */
public class CharArrayToListDemo {

    /**
     * Mutable list from a {@code Character[]} via {@code Arrays.asList}.
     */
    public static List<Character> listFromCharacterArray(Character[] charArray) {
        // Fixed-size view first, then copy into a growable ArrayList
        return new ArrayList<>(Arrays.asList(charArray));
    }

    /**
     * List of every character in a word or sentence (including spaces/punctuation).
     */
    public static List<Character> listFromText(String text) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>();
        }
        List<Character> list = new ArrayList<>(text.length());
        for (char c : text.toCharArray()) {
            list.add(c); // autobox char → Character
        }
        return list;
    }

    /**
     * Same idea using streams (Java 8+).
     */
    public static List<Character> listFromTextStream(String text) {
        if (text == null || text.isEmpty()) {
            return new ArrayList<>();
        }
        return text.chars()
                .mapToObj(c -> (char) c)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
    }

    public static void main(String[] args) {
        // --- From Character[] + Arrays.asList ---
        Character[] charArray = {'a', 'b', 'c'};
        List<Character> list = new ArrayList<>(Arrays.asList(charArray));
        System.out.println("From Character[]: " + list);
        list.add('d'); // OK — mutable ArrayList copy
        System.out.println("After add('d'):   " + list);

        // --- Wrong: primitive char[] with Arrays.asList ---
        char[] primitive = {'x', 'y', 'z'};
        List<char[]> wrong = Arrays.asList(primitive); // one element: the whole array
        System.out.println("Primitive char[] asList size (wrong for letters): " + wrong.size());

        // --- From a word / sentence ---
        String word = "Hello";
        String sentence = "Hi there!";
        System.out.println("From word \"" + word + "\":     " + listFromText(word));
        System.out.println("From sentence \"" + sentence + "\": " + listFromText(sentence));
        System.out.println("Stream version:               " + listFromTextStream(sentence));
    }
}
