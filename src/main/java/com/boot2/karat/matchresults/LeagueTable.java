package com.boot2.karat.matchresults;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Builds team standings from a list of {@link MatchResult}s.
 * <p>
 * <b>HashMap ordering trap (common Karat follow-up)</b>
 * <ul>
 *   <li>{@link HashMap} — no guaranteed iteration order. Printing "results" from a HashMap
 *       can look random / different between runs. Do NOT use HashMap when the answer must be ordered.</li>
 *   <li>{@link LinkedHashMap} — preserves insertion order (order teams first appeared).</li>
 *   <li>Sorted list / {@link TreeMap} with a comparator — order by total points, then avg, then name.</li>
 * </ul>
 * Interview tip: accumulate in a {@code Map<String, TeamStanding>}, then produce a
 * <i>sorted</i> {@code List} for the final leaderboard — do not rely on HashMap iteration.
 */
public class LeagueTable {

    private final Map<String, TeamStanding> byTeam = new HashMap<>();

    public void record(MatchResult result) {
        byTeam.computeIfAbsent(result.getTeamName(), TeamStanding::new).apply(result);
    }

    public void recordAll(List<MatchResult> results) {
        for (MatchResult result : results) {
            record(result);
        }
    }

    public TeamStanding getStanding(String teamName) {
        return byTeam.get(teamName);
    }

    public Map<String, TeamStanding> asHashMap() {
        return Collections.unmodifiableMap(byTeam);
    }

    /**
     * Insertion-order view: teams appear in the order they were first recorded.
     * Useful when Karat says "keep the order matches were processed".
     */
    public LinkedHashMap<String, TeamStanding> asLinkedHashMapPreservingFirstSeen(List<MatchResult> resultsInOrder) {
        LinkedHashMap<String, TeamStanding> ordered = new LinkedHashMap<>();
        for (MatchResult result : resultsInOrder) {
            ordered.computeIfAbsent(result.getTeamName(), name -> byTeam.get(name));
        }
        return ordered;
    }

    /**
     * Leaderboard sorted by: total points DESC, average points DESC, team name ASC.
     * This is the usual expected "ordered results" answer — not HashMap iteration.
     */
    public List<TeamStanding> leaderboard() {
        List<TeamStanding> list = new ArrayList<>(byTeam.values());
        list.sort(
                Comparator.comparingInt(TeamStanding::getTotalPoints).reversed()
                        .thenComparing(Comparator.comparingDouble(TeamStanding::getAveragePoints).reversed())
                        .thenComparing(TeamStanding::getTeamName));
        return list;
    }

    /**
     * TreeMap keyed by team name (alphabetical). Not a points ranking —
     * shown only to contrast "sorted keys" vs "sorted by points".
     */
    public TreeMap<String, TeamStanding> asTreeMapByTeamName() {
        return new TreeMap<>(byTeam);
    }
}
