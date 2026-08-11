package com.boot2.booleancheck;

/**
 * Tutorial: fixing a broken {@code isTrue(Boolean)} method.
 *
 * <p>The flawed version below has both <b>syntax (line-level) errors</b> and
 * <b>logical errors</b>. This class documents each problem and shows a correct
 * implementation.</p>
 *
 * <h2>Original broken code</h2>
 * <pre>{@code
 * public boolean isTrue(Boolean input) {
 *     if( !input.equals(Boolean.FALSE){   // syntax + logic problems
 *         return true;
 *     } else {
 *         return false;
 *     }
 * }
 * }</pre>
 */
public class BooleanIsTrueFix {

    /**
     * BROKEN reference implementation kept for study — do not call in production.
     *
     * <h3>Line-level / syntax errors</h3>
     * <ul>
     *   <li>Missing closing {@code )} before <code>{</code>:
     *       written as {@code if (!input.equals(Boolean.FALSE){} but required
     *       {@code if (!input.equals(Boolean.FALSE)) {}}.</li>
     *   <li>That alone prevents compilation.</li>
     * </ul>
     *
     * <h3>Logical errors</h3>
     * <ul>
     *   <li><b>NullPointerException risk:</b> {@code input.equals(...)} dereferences
     *       {@code input}. When {@code input} is {@code null}, the method crashes
     *       instead of returning a boolean.</li>
     *   <li><b>Odd null semantics if rewritten null-safely:</b>
     *       {@code !Objects.equals(input, Boolean.FALSE)} treats {@code null} as
     *       “not false” and would return {@code true}. Usually “is true?” should
     *       answer {@code false} for {@code null}.</li>
     *   <li><b>Needlessly complex:</b> if/else returning true/false duplicates what
     *       a single boolean expression already says.</li>
     * </ul>
     *
     * @param input wrapper flag that may be {@code null}
     * @return intended meaning is “is this true?”, but this version is unsafe/wrong
     * @throws NullPointerException when {@code input} is {@code null}
     */
    @SuppressWarnings("unused")
    private boolean isTrueBroken(Boolean input) {
        // Intentionally incorrect — mirrors the interview snippet after fixing only
        // enough syntax so the file compiles, while keeping the null/logic flaws.
        if (!input.equals(Boolean.FALSE)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Correct implementation: returns whether {@code input} is explicitly {@code true}.
     *
     * <h3>Why this version is correct</h3>
     * <ul>
     *   <li>{@code Boolean.TRUE.equals(input)} never calls a method on {@code input},
     *       so {@code null} is safe and yields {@code false}.</li>
     *   <li>{@code Boolean.TRUE} → {@code true}; {@code Boolean.FALSE} → {@code false};
     *       {@code null} → {@code false}.</li>
     *   <li>No redundant if/else; the expression already is the answer.</li>
     * </ul>
     *
     * <pre>{@code
     * isTrue(Boolean.TRUE)  → true
     * isTrue(Boolean.FALSE) → false
     * isTrue(null)          → false   // no NPE
     * }</pre>
     *
     * @param input boxed boolean; may be {@code null}
     * @return {@code true} only when {@code input} is {@link Boolean#TRUE}
     */
    public boolean isTrue(Boolean input) {
        // Call equals on the constant, not on input → null-safe
        return Boolean.TRUE.equals(input);
    }

    /**
     * Demo of the correct method (and what the broken one does on null).
     */
    public static void main(String[] args) {
        BooleanIsTrueFix fix = new BooleanIsTrueFix();

        System.out.println("isTrue(TRUE)  = " + fix.isTrue(Boolean.TRUE));
        System.out.println("isTrue(FALSE) = " + fix.isTrue(Boolean.FALSE));
        System.out.println("isTrue(null)  = " + fix.isTrue(null));

        try {
            fix.isTrueBroken(null);
        } catch (NullPointerException npe) {
            System.out.println("isTrueBroken(null) threw NullPointerException (expected)");
        }
    }
}
