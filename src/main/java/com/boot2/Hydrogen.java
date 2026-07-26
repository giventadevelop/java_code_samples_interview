package com.boot2;

/**
 * Continuous Hydrogen source worker.
 *
 * Each instance runs on its own thread and represents one H atom arriving
 * at the {@link WaterFactory} pipeline junction. The factory's Semaphore +
 * CyclicBarrier decide when this atom may combine into water (needs a partner H + one O).
 *
 * Interview talking point: the atom thread only knows "I am H"; all chemistry
 * rules live in WaterFactory (single place for synchronization).
 */
public class Hydrogen implements Runnable {

    private final WaterFactory waterFactory;

    public Hydrogen(WaterFactory waterFactory) {
        this.waterFactory = waterFactory;
    }

    @Override
    public void run() {
        try {
            // Callback runs only after barrier trips → this H is part of a valid H2O.
            waterFactory.hydrogen(() -> waterFactory.appendAtom('H'));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(Thread.currentThread().getName() + " interrupted as Hydrogen");
        }
    }
}
