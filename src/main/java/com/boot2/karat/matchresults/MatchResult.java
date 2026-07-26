package com.boot2.karat.matchresults;

/**
 * One team's recorded result for a match (Karat-style domain object).
 * Stores who played, the outcome (win / draw / loss), and the points earned.
 */
public class MatchResult {

    private final String matchId;
    private final String teamName;
    private final MatchOutcome outcome;
    private final int pointsEarned;

    public MatchResult(String matchId, String teamName, MatchOutcome outcome) {
        this.matchId = matchId;
        this.teamName = teamName;
        this.outcome = outcome;
        this.pointsEarned = outcome.getPoints();
    }

    public String getMatchId() {
        return matchId;
    }

    public String getTeamName() {
        return teamName;
    }

    public MatchOutcome getOutcome() {
        return outcome;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

    @Override
    public String toString() {
        return "MatchResult{matchId='" + matchId + "', team='" + teamName
                + "', outcome=" + outcome + ", points=" + pointsEarned + "}";
    }
}
