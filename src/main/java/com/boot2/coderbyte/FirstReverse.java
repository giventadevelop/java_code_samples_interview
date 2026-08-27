package com.boot2.coderbyte;

/**
 * First Reverse — Easy.
 *
 * <p>Return the input string reversed (e.g. {@code "coderbyte" → "etybredoc"}).</p>
 */
public final class FirstReverse {

    private FirstReverse() {
    }

    public static String FirstReverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }
}
