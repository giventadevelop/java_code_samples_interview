package com.boot2.h2o;

/**
 * Continuous Oxygen source worker.
 *
 * Each instance runs on its own thread and represents one O atom arriving
 * at the {@link WaterFactory} pipeline. Only one O is allowed in the chamber
 * at a time (oxygenSemaphore permit = 1), matching H2O chemistry.
 */
public class Oxygen implements Runnable {

    private final WaterFactory waterFactory;

    public Oxygen(WaterFactory waterFactory) {
        this.waterFactory = waterFactory;
    }

    @Override
    public void run() {
        try {
            waterFactory.oxygen(() -> waterFactory.appendAtom('O'));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println(Thread.currentThread().getName() + " interrupted as Oxygen");
        }
    }
}
