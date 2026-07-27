package com.boot2.h2o;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Interview classic: continuous water pipeline (H2O).
 *
 * PROBLEM
 * -------
 * Many Hydrogen and Oxygen "atoms" arrive on separate threads (a continuous source).
 * Water forms only when exactly 2 Hydrogen + 1 Oxygen combine.
 * You must synchronize so you never emit invalid sequences like HHH, OO, HOHO with
 * wrong grouping, etc. Output for N molecules may be any order of H/H/O within each
 * molecule, but globally the ratio must always be valid.
 *
 * ANALOGY
 * -------
 * Think of a pipe junction (this class) where H and O streams meet.
 * Semaphores are the valves that limit how many of each gas enter the chamber.
 * CyclicBarrier is the spark that fires only when the chamber has a full set (2H+1O).
 *
 * KEY JDK TOOLS USED HERE
 * -----------------------
 * {@link Semaphore}
 *   - Counting permit pool. {@code acquire()} blocks until a permit is free;
 *     {@code release()} returns a permit.
 *   - Unlike a mutex (1 permit), a semaphore can allow N concurrent holders.
 *   - Here: hydrogenSemaphore starts with 2 permits → at most 2 H wait in chamber.
 *            oxygenSemaphore starts with 1 permit  → at most 1 O waits in chamber.
 *   - This prevents flooding: e.g. 100 H threads cannot all pile in ahead of O.
 *
 * {@link CyclicBarrier}
 *   - Parties (threads) call {@code await()} and block until ALL parties arrive.
 *   - When the Nth party arrives, the barrier "trips", all are released, then it RESETS
 *     (cyclic) so the next group of N can synchronize again — perfect for a continuous pipeline.
 *   - Here: parties = 3 (exactly 2H + 1O). Only when all three await() does water form.
 *   - Contrast {@link java.util.concurrent.CountDownLatch}: latch does not reset;
 *     barrier is reusable for molecule after molecule.
 *
 * WHY BOTH?
 * ---------
 * Barrier alone does not stop 3 Hydrogens from being the three waiters.
 * Semaphores enforce the chemistry rule (2H + 1O); barrier enforces "release together".
 */
public class WaterFactory {

    /**
     * Valve for Hydrogen: max 2 permits in the reaction chamber at once.
     * Extra H threads block on acquire() until a previous molecule finishes and releases permits.
     */
    private final Semaphore hydrogenSemaphore = new Semaphore(2);

    /**
     * Valve for Oxygen: max 1 permit in the reaction chamber at once.
     */
    private final Semaphore oxygenSemaphore = new Semaphore(1);

    /**
     * Sync point for one molecule: 3 parties (2H + 1O).
     * After trip, barrier resets automatically for the next molecule (continuous source).
     *
     * Optional barrier action runs once when the barrier trips — useful to count water formed.
     */
    private final CyclicBarrier moleculeBarrier = new CyclicBarrier(3, this::onWaterFormed);

    /** How many water molecules have been successfully formed. */
    private final AtomicInteger waterMoleculesFormed = new AtomicInteger(0);

    /** Shared output buffer so the demo can show the continuous stream (thread-safe append). */
    private final StringBuilder pipelineOutput = new StringBuilder();

    /**
     * Called exactly once each time 2H + 1O all reach await() — i.e. one H2O formed.
     * Runs on the thread that trips the barrier (the last of the three to arrive).
     */
    private void onWaterFormed() {
        int n = waterMoleculesFormed.incrementAndGet();
        System.out.println(" -> Water molecule #" + n + " formed (2H + 1O)");
    }

    /**
     * Hydrogen atom enters the pipeline.
     * Steps: take an H permit → wait at barrier for full H2O → release H into output → free permit.
     *
     * @param releaseHydrogen typically prints/appends "H" (LeetCode-style callback)
     */
    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        // Block if 2 Hydrogens are already waiting for Oxygen (no more H into chamber).
        hydrogenSemaphore.acquire();
        try {
            // Wait until exactly 3 atoms (2H+1O) are ready; then all three proceed together.
            moleculeBarrier.await();
            // Only after barrier trips do we "release" this atom into the water stream.
            releaseHydrogen.run();
        } catch (BrokenBarrierException e) {
            // Barrier broken (e.g. another thread interrupted) — restore interrupt semantics.
            Thread.currentThread().interrupt();
            throw new InterruptedException("Molecule barrier broken while waiting as Hydrogen");
        } finally {
            // Always return the H permit so the continuous pipeline can accept the next H.
            hydrogenSemaphore.release();
        }
    }

    /**
     * Oxygen atom enters the pipeline.
     * Steps: take the O permit → wait at barrier for full H2O → release O into output → free permit.
     *
     * @param releaseOxygen typically prints/appends "O"
     */
    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        oxygenSemaphore.acquire();
        try {
            moleculeBarrier.await();
            releaseOxygen.run();
        } catch (BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedException("Molecule barrier broken while waiting as Oxygen");
        } finally {
            oxygenSemaphore.release();
        }
    }

    public int getWaterMoleculesFormed() {
        return waterMoleculesFormed.get();
    }

    public String getPipelineOutput() {
        synchronized (pipelineOutput) {
            return pipelineOutput.toString();
        }
    }

    void appendAtom(char atom) {
        synchronized (pipelineOutput) {
            pipelineOutput.append(atom);
        }
        System.out.print(atom);
    }

    /**
     * Demo: continuous sources of H and O threads combine into water via this factory.
     * Input string characters are atoms arriving on the pipeline (e.g. "OOHHHH" → 2 water).
     *
     * Run: {@code java -cp target/classes com.boot2.h2o.WaterFactory}
     * or run this main from the IDE.
     */
    public static void main(String[] args) throws InterruptedException {
        // Continuous feed: enough atoms for several water molecules (must be 2H : 1O overall).
        String atomFeed = args.length > 0 ? args[0] : "HOHHOHHOHH";
        int expectedMolecules = countExpectedMolecules(atomFeed);

        System.out.println("=== Water Pipeline Simulator (Semaphore + CyclicBarrier) ===");
        System.out.println("Atom feed : " + atomFeed);
        System.out.println("Expected  : " + expectedMolecules + " water molecule(s)");
        System.out.println("Stream    : ");

        WaterFactory factory = new WaterFactory();
        Thread[] workers = new Thread[atomFeed.length()];

        for (int i = 0; i < atomFeed.length(); i++) {
            char atom = atomFeed.charAt(i);
            if (atom == 'H' || atom == 'h') {
                // Each Hydrogen is its own continuous-source worker thread.
                workers[i] = new Thread(new Hydrogen(factory), "H-" + i);
            } else if (atom == 'O' || atom == 'o') {
                workers[i] = new Thread(new Oxygen(factory), "O-" + i);
            } else {
                throw new IllegalArgumentException("Only H and O allowed, got: " + atom);
            }
            workers[i].start();
        }

        for (Thread worker : workers) {
            worker.join();
        }

        System.out.println();
        System.out.println("Pipeline output string : " + factory.getPipelineOutput());
        System.out.println("Molecules formed       : " + factory.getWaterMoleculesFormed());
        System.out.println("Valid continuous H2O?  : "
                + (factory.getWaterMoleculesFormed() == expectedMolecules));
    }

    private static int countExpectedMolecules(String feed) {
        int h = 0;
        int o = 0;
        for (char c : feed.toCharArray()) {
            if (c == 'H' || c == 'h') {
                h++;
            } else if (c == 'O' || c == 'o') {
                o++;
            }
        }
        // Chemistry: each water needs 2H and 1O; leftover atoms cannot form a full molecule.
        return Math.min(h / 2, o);
    }
}
