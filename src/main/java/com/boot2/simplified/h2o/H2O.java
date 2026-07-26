package com.boot2.simplified.h2o;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * Classic H2O / Building Water — simplified interview version.
 *
 * <p>Full pipeline demo with workers lives in {@code com.boot2.WaterFactory}.
 * This package is the short Semaphore + CyclicBarrier form you can write in an interview.
 *
 * <h2>Problem</h2>
 * Threads call {@link #hydrogen} or {@link #oxygen}. Allow them to proceed only in groups of
 * <b>2 Hydrogen + 1 Oxygen</b> (one water molecule).
 *
 * <h2>Idea</h2>
 * <ul>
 *   <li>Cap how many H and O enter the reaction chamber ({@link Semaphore}).</li>
 *   <li>Wait until the chamber has a full set H, H, O ({@link CyclicBarrier} of size 3).</li>
 *   <li>Then release them together as one water molecule.</li>
 * </ul>
 *
 * <h2>Why both?</h2>
 * Barrier alone could let 3 Hydrogens through. Semaphores enforce 2H + 1O;
 * the barrier makes those three proceed together.
 */
public class H2O {

    /** At most 2 Hydrogen atoms waiting in the chamber. */
    private final Semaphore hSem = new Semaphore(2);

    /** At most 1 Oxygen atom waiting in the chamber. */
    private final Semaphore oSem = new Semaphore(1);

    /** All 3 atoms (2H + 1O) must arrive before any of them "releases" into water. */
    private final CyclicBarrier barrier = new CyclicBarrier(3);

    /**
     * Hydrogen atom: take an H permit → wait for full H2O → print/release H → free permit.
     */
    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        hSem.acquire();
        try {
            barrier.await();
            releaseHydrogen.run(); // typically prints "H"
        } catch (BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedException("H2O barrier broken (hydrogen)");
        } finally {
            hSem.release();
        }
    }

    /**
     * Oxygen atom: take the O permit → wait for full H2O → print/release O → free permit.
     */
    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        oSem.acquire();
        try {
            barrier.await();
            releaseOxygen.run(); // typically prints "O"
        } catch (BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            throw new InterruptedException("H2O barrier broken (oxygen)");
        } finally {
            oSem.release();
        }
    }
}
