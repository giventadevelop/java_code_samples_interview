package com.boot2.karat.matchresults;

/**
 * Administrator who evaluates whether a match result / team standing is accepted or denied.
 * <p>
 * Typical Karat rules (inferred — prompts vary; keep thresholds easy to change):
 * <ul>
 *   <li>Reject if average points are below a minimum (poor performance / suspicious under-reporting).</li>
 *   <li>Reject if losses exceed a max allowed relative to wins (e.g. more losses than wins + draws).</li>
 *   <li>Reject if the team has not played enough matches to be evaluated.</li>
 *   <li>Otherwise approve.</li>
 * </ul>
 */
public class MatchAdministrator {

    private final double minAveragePoints;
    private final int minMatchesRequired;
    private final boolean requireWinsNotLessThanLosses;

    public MatchAdministrator(double minAveragePoints, int minMatchesRequired, boolean requireWinsNotLessThanLosses) {
        this.minAveragePoints = minAveragePoints;
        this.minMatchesRequired = minMatchesRequired;
        this.requireWinsNotLessThanLosses = requireWinsNotLessThanLosses;
    }

    /**
     * Default Karat-style policy:
     * avg &gt;= 1.0, at least 2 matches, and wins &gt;= losses.
     */
    public static MatchAdministrator defaultPolicy() {
        return new MatchAdministrator(1.0, 2, true);
    }

    public EvaluationResult evaluate(TeamStanding standing) {
        if (standing.getMatchesPlayed() < minMatchesRequired) {
            return EvaluationResult.rejected(
                    standing.getTeamName(),
                    "Insufficient matches played (" + standing.getMatchesPlayed()
                            + " < " + minMatchesRequired + ")");
        }

        if (standing.getAveragePoints() < minAveragePoints) {
            return EvaluationResult.rejected(
                    standing.getTeamName(),
                    String.format(
                            "Average points %.2f below minimum %.2f (points=%d, played=%d)",
                            standing.getAveragePoints(),
                            minAveragePoints,
                            standing.getTotalPoints(),
                            standing.getMatchesPlayed()));
        }

        if (requireWinsNotLessThanLosses && standing.getWins() < standing.getLosses()) {
            return EvaluationResult.rejected(
                    standing.getTeamName(),
                    "More losses (" + standing.getLosses() + ") than wins (" + standing.getWins() + ")");
        }

        return EvaluationResult.approved(
                standing.getTeamName(),
                String.format(
                        "OK: avg=%.2f, W=%d D=%d L=%d, pts=%d",
                        standing.getAveragePoints(),
                        standing.getWins(),
                        standing.getDraws(),
                        standing.getLosses(),
                        standing.getTotalPoints()));
    }

    /**
     * Immutable evaluation payload for demos / assertions.
     */
    public static final class EvaluationResult {
        private final String teamName;
        private final AdminDecision decision;
        private final String reason;

        private EvaluationResult(String teamName, AdminDecision decision, String reason) {
            this.teamName = teamName;
            this.decision = decision;
            this.reason = reason;
        }

        public static EvaluationResult approved(String teamName, String reason) {
            return new EvaluationResult(teamName, AdminDecision.APPROVED, reason);
        }

        public static EvaluationResult rejected(String teamName, String reason) {
            return new EvaluationResult(teamName, AdminDecision.REJECTED, reason);
        }

        public String getTeamName() {
            return teamName;
        }

        public AdminDecision getDecision() {
            return decision;
        }

        public String getReason() {
            return reason;
        }

        public boolean isApproved() {
            return decision == AdminDecision.APPROVED;
        }

        @Override
        public String toString() {
            return teamName + " -> " + decision + " | " + reason;
        }
    }
}
