package com.boot2.simplified.h2o;

/**
 * Tiny driver for the simplified {@link H2O} solution.
 *
 * <p>Run:
 * {@code mvn -q -DskipTests compile}
 * {@code java -cp target/classes com.boot2.simplified.h2o.H2ODemo}
 *
 * <p>Input atoms (e.g. {@code HOH}) each run on their own thread. Valid output is any order
 * of H/H/O <i>per molecule</i>, but never an invalid ratio overall.
 */
public class H2ODemo {

    public static void main(String[] args) throws InterruptedException {
        String feed = args.length > 0 ? args[0] : "HOH";
        System.out.println("Simplified H2O demo — atom feed: " + feed);
        System.out.print("Output: ");

        H2O h2o = new H2O();
        Thread[] workers = new Thread[feed.length()];

        for (int i = 0; i < feed.length(); i++) {
            char atom = feed.charAt(i);
            if (atom == 'H' || atom == 'h') {
                workers[i] = new Thread(() -> {
                    try {
                        h2o.hydrogen(() -> System.out.print("H"));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "H-" + i);
            } else if (atom == 'O' || atom == 'o') {
                workers[i] = new Thread(() -> {
                    try {
                        h2o.oxygen(() -> System.out.print("O"));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, "O-" + i);
            } else {
                throw new IllegalArgumentException("Only H/O allowed: " + atom);
            }
            workers[i].start();
        }

        for (Thread t : workers) {
            t.join();
        }
        System.out.println();
        System.out.println("(Any valid permutation of one H2O molecule is OK, e.g. HOH / OHH / HHO)");
    }
}
