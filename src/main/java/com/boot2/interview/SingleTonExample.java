package com.boot2.interview;

/**
 * Singleton options — from classic synchronized DCL to newer / preferred techniques.
 *
 * <h2>Which is better?</h2>
 * <ol>
 *   <li><b>Enum singleton</b> ({@link SingleTonEnum}) — preferred in modern Java
 *       (Effective Java). Simple, lazy-enough, thread-safe, resists reflection and
 *       serialization tricks.</li>
 *   <li><b>Initialization-on-demand holder</b> ({@link SingleTonHolder}) — best
 *       <em>class-based</em> lazy singleton: no {@code synchronized} on the hot path;
 *       JVM class-loading gives the lock.</li>
 *   <li><b>Eager {@code static final}</b> ({@link SingleTonEager}) — simplest if
 *       you always need the instance and construction is cheap.</li>
 *   <li><b>Double-checked locking</b> ({@link #getInstance()}) — works only if the
 *       field is {@code volatile}; more error-prone than holder/enum. Avoid in new code.</li>
 * </ol>
 *
 * <p>Spring apps usually let the container manage a {@code @Service}/{@code @Component}
 * singleton bean instead of hand-rolling one.</p>
 */
public final class SingleTonExample {

    /**
     * Must be {@code volatile} for correct double-checked locking: without it, another
     * thread can see a non-null reference before the constructor finished (unsafe publication).
     */
    private static volatile SingleTonExample SINGLE_TON_EXAMPLE;

    private SingleTonExample() {
    }

    /**
     * Classic double-checked locking (DCL).
     *
     * <p><b>Why people used it:</b> avoid synchronizing on every call after the instance exists.</p>
     *
     * <p><b>Why it is not the best anymore:</b></p>
     * <ul>
     *   <li>Easy to get wrong (forgetting {@code volatile}).</li>
     *   <li>More code than holder or enum for the same guarantees.</li>
     *   <li>Still uses {@code synchronized} on the first creation path.</li>
     * </ul>
     *
     * <p>Prefer {@link SingleTonHolder#getInstance()} or {@link SingleTonEnum#INSTANCE}.</p>
     */
    public static SingleTonExample getInstance() {
        if (SINGLE_TON_EXAMPLE == null) {
            synchronized (SingleTonExample.class) {
                if (SINGLE_TON_EXAMPLE == null) {
                    SINGLE_TON_EXAMPLE = new SingleTonExample();
                }
            }
        }
        return SINGLE_TON_EXAMPLE;
    }

    // -------------------------------------------------------------------------
    // Newer / better alternatives
    // -------------------------------------------------------------------------

    /**
     * Eager singleton: instance created when the class is loaded.
     * Thread-safe, dead simple — use when lazy init is not required.
     */
    public static final class SingleTonEager {

        private static final SingleTonEager INSTANCE = new SingleTonEager();

        private SingleTonEager() {
        }

        public static SingleTonEager getInstance() {
            return INSTANCE;
        }
    }

    /**
     * Initialization-on-demand holder (Bill Pugh) — <b>best lazy class-based singleton</b>.
     *
     * <p>No {@code synchronized} in {@code getInstance()}. The nested {@code Holder} class
     * loads only on first call; JVM class initialization is thread-safe, so the instance
     * is created exactly once.</p>
     */
    public static final class SingleTonHolder {
        private SingleTonHolder() {
        }

        private static final class Holder {
            private static final SingleTonHolder INSTANCE = new SingleTonHolder();
        }

        public static SingleTonHolder getInstance() {
            return Holder.INSTANCE;
        }
    }

    /**
     * Enum singleton — <b>preferred modern technique</b> (Effective Java).
     *
     * <ul>
     *   <li>Concise and inherently thread-safe.</li>
     *   <li>JVM guarantees a single instance even with reflection / serialization.</li>
     *   <li>Access: {@code SingleTonEnum.INSTANCE}</li>
     * </ul>
     */
    public enum SingleTonEnum {
        INSTANCE;

        public void doWork() {
            // example business method
        }
    }

    public static void main(String[] args) {
        SingleTonExample a = SingleTonExample.getInstance();
        SingleTonExample b = SingleTonExample.getInstance();
        System.out.println("DCL same instance: " + (a == b));

        System.out.println("Holder same: "
                + (SingleTonHolder.getInstance() == SingleTonHolder.getInstance()));
        System.out.println("Eager same: "
                + (SingleTonEager.getInstance() == SingleTonEager.getInstance()));
        System.out.println("Enum same: "
                + (SingleTonEnum.INSTANCE == SingleTonEnum.INSTANCE));
    }
}
