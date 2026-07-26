package com.boot2.karat.matchresults;

/**
 * Outcome of a single finished match for one team (or side).
 * <p>
 * Point awards (classic league style — adjust if the Karat prompt differs):
 * <ul>
 *   <li>WIN  = 3 points</li>
 *   <li>DRAW = 1 point</li>
 *   <li>LOSS = 0 points</li>
 * </ul>
 */
public enum MatchOutcome {
    WIN(3),
    DRAW(1),
    LOSS(0);

    private final int points;

    MatchOutcome(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
