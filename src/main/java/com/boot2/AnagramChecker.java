package com.boot2;

import java.util.stream.Collectors;

public class AnagramChecker {
    public static boolean isAnagram(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }

        return str1.chars()
                .boxed()
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                .equals(
                        str2.chars()
                                .boxed()
                                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                );
    }

    public static void main(String[] args) {
        String word1 = "listen";
        String word2 = "silent";

        System.out.println(isAnagram(word1, word2)); // true
    }
}
