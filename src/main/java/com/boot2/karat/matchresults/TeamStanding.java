package com.boot2.karat.matchresults;

/**
 * Aggregated standing for one team: wins / draws / losses, total points,
 * and average points per match (totalPoints / matchesPlayed).
 * <p>
 * Karat prompts often ask for "average points" — that is usually
 * {@code totalPoints / numberOfMatches}, not a nested average-of-averages,
 * unless the prompt explicitly says otherwise.
 */
public class TeamStanding {

    private final String teamName;
    private int wins;
    private int draws;
    private int losses;
    private int totalPoints;

    public TeamStanding(String teamName) {
        this.teamName = teamName;
    }

    public void apply(MatchResult result) {
        if (!teamName.equals(result.getTeamName())) {
            throw new IllegalArgumentException("Result team does not match standing: " + result.getTeamName());
        }
        switch (result.getOutcome()) {
            case WIN -> wins++;
            case DRAW -> draws++;
            case LOSS -> losses++;
        }
        totalPoints += result.getPointsEarned();
    }

    public String getTeamName() {
        return teamName;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }

    public int getLosses() {
        return losses;
    }

    public int getMatchesPlayed() {
        return wins + draws + losses;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    /**
     * Average points per match. Returns 0.0 if the team has not played yet.
     */
    public double getAveragePoints() {
        int played = getMatchesPlayed();
        if (played == 0) {
            return 0.0;
        }
        return (double) totalPoints / played;
    }

    @Override
    public String toString() {
        return String.format(
                "%s  W=%d D=%d L=%d  pts=%d  avg=%.2f  played=%d",
                teamName, wins, draws, losses, totalPoints, getAveragePoints(), getMatchesPlayed());
    }
}
