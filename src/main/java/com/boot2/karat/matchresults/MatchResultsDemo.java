package com.boot2.karat.matchresults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Runnable demo for the inferred Karat "Match Results" question.
 * <p>
 * Run:
 * {@code mvn -q -DskipTests compile}
 * {@code java -cp target/classes com.boot2.karat.matchresults.MatchResultsDemo}
 */
public class MatchResultsDemo {

    public static void main(String[] args) {
        System.out.println("=== Karat-style Match Results Demo ===");
        System.out.println();

        List<MatchResult> results = sampleResults();
        printRawResults(results);

        LeagueTable table = new LeagueTable();
        table.recordAll(results);

        System.out.println("--- 1) HashMap iteration (NOT reliable order) ---");
        System.out.println("HashMap does not guarantee order. Do not submit this as a ranked table.");
        Map<String, TeamStanding> hashView = table.asHashMap();
        for (Map.Entry<String, TeamStanding> e : hashView.entrySet()) {
            System.out.println("  " + e.getValue());
        }
        System.out.println();

        System.out.println("--- 2) LinkedHashMap (first-seen / processing order) ---");
        LinkedHashMap<String, TeamStanding> linked = table.asLinkedHashMapPreservingFirstSeen(results);
        for (TeamStanding s : linked.values()) {
            System.out.println("  " + s);
        }
        System.out.println();

        System.out.println("--- 3) Leaderboard (sort by points, then avg) — preferred answer ---");
        List<TeamStanding> board = table.leaderboard();
        int rank = 1;
        for (TeamStanding s : board) {
            System.out.println("  #" + rank++ + "  " + s);
        }
        System.out.println();

        System.out.println("--- 4) Administrator approve / reject ---");
        MatchAdministrator admin = MatchAdministrator.defaultPolicy();
        for (TeamStanding s : board) {
            MatchAdministrator.EvaluationResult eval = admin.evaluate(s);
            System.out.println("  " + eval);
        }
        System.out.println();

        System.out.println("--- 5) Expected evaluation snapshot ---");
        System.out.println("  Alpha  : APPROVED  (strong avg / wins >= losses)");
        System.out.println("  Bravo  : often APPROVED if avg >= 1.0 and wins >= losses");
        System.out.println("  Charlie: REJECTED  (more losses than wins and/or low avg)");
        System.out.println("  Delta  : REJECTED  (only 1 match < minMatchesRequired=2)");
        System.out.println();

        assertDemoExpectations(table, admin);
        System.out.println("Demo assertions passed.");
    }

    /**
     * Sample season scrapes — mirrors a typical Karat input list of match outcomes.
     */
    static List<MatchResult> sampleResults() {
        List<MatchResult> results = new ArrayList<>();
        // Alpha: W, W, D  -> pts 7, avg 2.33, W2 D1 L0
        results.add(new MatchResult("M1", "Alpha", MatchOutcome.WIN));
        results.add(new MatchResult("M2", "Alpha", MatchOutcome.WIN));
        results.add(new MatchResult("M3", "Alpha", MatchOutcome.DRAW));
        // Bravo: W, D, L  -> pts 4, avg 1.33, W1 D1 L1
        results.add(new MatchResult("M1", "Bravo", MatchOutcome.LOSS)); // vs Alpha
        results.add(new MatchResult("M4", "Bravo", MatchOutcome.WIN));
        results.add(new MatchResult("M5", "Bravo", MatchOutcome.DRAW));
        // Charlie: L, L, D -> pts 1, avg 0.33, W0 D1 L2  -> reject
        results.add(new MatchResult("M4", "Charlie", MatchOutcome.LOSS));
        results.add(new MatchResult("M6", "Charlie", MatchOutcome.LOSS));
        results.add(new MatchResult("M7", "Charlie", MatchOutcome.DRAW));
        // Delta: single WIN only -> reject (not enough matches)
        results.add(new MatchResult("M8", "Delta", MatchOutcome.WIN));
        return results;
    }

    private static void printRawResults(List<MatchResult> results) {
        System.out.println("--- Input MatchResult list ---");
        for (MatchResult r : results) {
            System.out.println("  " + r);
        }
        System.out.println();
    }

    private static void assertDemoExpectations(LeagueTable table, MatchAdministrator admin) {
        TeamStanding alpha = table.getStanding("Alpha");
        TeamStanding charlie = table.getStanding("Charlie");
        TeamStanding delta = table.getStanding("Delta");

        if (alpha.getTotalPoints() != 7) {
            throw new IllegalStateException("Alpha points expected 7, got " + alpha.getTotalPoints());
        }
        if (!admin.evaluate(alpha).isApproved()) {
            throw new IllegalStateException("Alpha should be APPROVED");
        }
        if (admin.evaluate(charlie).isApproved()) {
            throw new IllegalStateException("Charlie should be REJECTED");
        }
        if (admin.evaluate(delta).isApproved()) {
            throw new IllegalStateException("Delta should be REJECTED (min matches)");
        }

        // Prove HashMap is unordered for ranking purposes: leaderboard must put Alpha first.
        List<TeamStanding> board = table.leaderboard();
        if (!"Alpha".equals(board.get(0).getTeamName())) {
            throw new IllegalStateException("Leaderboard #1 should be Alpha");
        }

        // Accidental "ordering via HashMap" is wrong — we only assert leaderboard order above.
        Map<String, Integer> bogusOrderCheck = new HashMap<>();
        bogusOrderCheck.put("Charlie", 1);
        bogusOrderCheck.put("Alpha", 2);
        bogusOrderCheck.put("Bravo", 3);
        // Just ensure map builds; iteration order is intentionally ignored.
        if (bogusOrderCheck.size() != 3) {
            throw new IllegalStateException("HashMap size check failed");
        }
    }
}
