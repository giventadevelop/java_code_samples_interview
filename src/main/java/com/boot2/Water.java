package com.boot2;

/**
 * Result of one successful combine step: Water = 2 {@link Hydrogen} + 1 {@link Oxygen}.
 *
 * In the concurrent pipeline demo, molecules are counted inside {@link WaterFactory}
 * via CyclicBarrier's barrier-action (onWaterFormed). This class is the domain model
 * you can mention in an interview if they ask "what object represents formed water?"
 */
public class Water {

    private final Hydrogen hydrogen1;
    private final Hydrogen hydrogen2;
    private final Oxygen oxygen;
    private final int moleculeNumber;

    public Water(Hydrogen hydrogen1, Hydrogen hydrogen2, Oxygen oxygen, int moleculeNumber) {
        this.hydrogen1 = hydrogen1;
        this.hydrogen2 = hydrogen2;
        this.oxygen = oxygen;
        this.moleculeNumber = moleculeNumber;
    }

    public int getMoleculeNumber() {
        return moleculeNumber;
    }

    public Hydrogen getHydrogen1() {
        return hydrogen1;
    }

    public Hydrogen getHydrogen2() {
        return hydrogen2;
    }

    public Oxygen getOxygen() {
        return oxygen;
    }

    @Override
    public String toString() {
        return "Water{molecule#" + moleculeNumber + ", formula=H2O}";
    }
}
