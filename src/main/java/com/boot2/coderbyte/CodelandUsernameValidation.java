package com.boot2.coderbyte;

/**
 * Codeland Username Validation — Easy.
 *
 * <ul>
 *   <li>Length 4–25</li>
 *   <li>Starts with a letter</li>
 *   <li>Only letters, digits, underscore</li>
 *   <li>Does not end with underscore</li>
 * </ul>
 *
 * <p>Returns {@code "true"} or {@code "false"}.</p>
 */
public final class CodelandUsernameValidation {

    private CodelandUsernameValidation() {
    }

    public static String CodelandUsernameValidation(String str) {
        if (str == null || str.length() < 4 || str.length() > 25) {
            return "false";
        }
        if (!Character.isLetter(str.charAt(0))) {
            return "false";
        }
        if (str.charAt(str.length() - 1) == '_') {
            return "false";
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!(Character.isLetterOrDigit(c) || c == '_')) {
                return "false";
            }
        }
        return "true";
    }
}
