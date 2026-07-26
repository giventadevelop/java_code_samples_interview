# Karat-style: Match Results (Win / Draw / Loss + Admin Review)

Package: `com.boot2.karat.matchresults`

This is an **inferred reconstruction** of a Karat coding screen that people often
remember as:

- a `MatchResult` (or similar) class holding **win / draw / loss**
- different **points** per outcome
- computing **total** and **average points**
- an **administrator** who **approves or rejects** based on points / W-D-L
- a gotcha where results were stored in a **`HashMap` and order looked wrong**

Karat prompts are private and vary by company. This package is a faithful
**practice version** of that family of questions — not a verbatim dump of a live test.

---

## What type of question is this?

| Category | Detail |
|----------|--------|
| Style | Karat / OA-style **domain modeling + aggregation + sorting** |
| Difficulty | Easy–medium (maps, enums, sorting, simple rules engine) |
| Skills tested | `HashMap` accumulation, average calculation, sorting/comparators, clear rules |
| Common trap | Using `HashMap` iteration as the “ordered” answer |

Similar public cousins:

- League / tournament standings from match outcomes  
- Sports points table (3 for win, 1 for draw, 0 for loss)  
- “Validate / filter teams that meet criteria” (admin approve/reject)

---

## Problem statement (practice wording)

You are given a list of match results. Each result records:

- `matchId`
- `teamName`
- `outcome` ∈ { `WIN`, `DRAW`, `LOSS` }

**Points**

| Outcome | Points |
|---------|--------|
| WIN | 3 |
| DRAW | 1 |
| LOSS | 0 |

For each team compute:

- wins, draws, losses  
- `totalPoints`  
- `averagePoints = totalPoints / matchesPlayed`

An **administrator** then evaluates each team:

1. Reject if `matchesPlayed < minMatchesRequired` (default 2)  
2. Reject if `averagePoints < minAveragePoints` (default 1.0)  
3. Reject if `wins < losses` (when that rule is enabled)  
4. Otherwise **APPROVED**

Finally produce a **leaderboard** ordered by:

1. total points DESC  
2. average points DESC  
3. team name ASC  

---

## HashMap ordering (the part people remember)

```text
HashMap          → NO guaranteed order  ❌ do not use for ranked output
LinkedHashMap    → insertion / first-seen order ✅ if prompt asks for process order
List + sort      → ranking by points/avg     ✅ usual leaderboard answer
TreeMap(by name) → alphabetical keys only    ⚠️ not the same as points ranking
```

**Interview one-liner:**  
Accumulate with `Map<String, TeamStanding>`, then `new ArrayList<>(values).sort(comparator)` —
never print `hashMap.entrySet()` and call it ordered results.

---

## Classes in this package

| Class | Role |
|-------|------|
| `MatchOutcome` | WIN/DRAW/LOSS + points |
| `MatchResult` | One team’s result for one match |
| `TeamStanding` | Aggregates W/D/L, total pts, average pts |
| `LeagueTable` | Builds standings; shows HashMap vs LinkedHashMap vs sorted leaderboard |
| `MatchAdministrator` | Approve / reject with reasons |
| `AdminDecision` | APPROVED / REJECTED |
| `MatchResultsDemo` | Runnable sample + assertions |

---

## Sample data & expected results

| Team | Results | Pts | Avg | Admin |
|------|---------|-----|-----|-------|
| Alpha | W, W, D | 7 | 2.33 | APPROVED |
| Bravo | L, W, D | 4 | 1.33 | APPROVED |
| Charlie | L, L, D | 1 | 0.33 | REJECTED (low avg and/or wins &lt; losses) |
| Delta | W | 3 | 3.00 | REJECTED (only 1 match) |

Leaderboard #1 should be **Alpha**.

---

## How to run

```text
mvn -q -DskipTests compile
java -cp target/classes com.boot2.karat.matchresults.MatchResultsDemo
```

---

## How to adapt if your real Karat prompt differed

- Points were 2/1/0 or 1/0.5/0 → change `MatchOutcome` constants  
- Admin used only total points, not average → change `MatchAdministrator.evaluate`  
- Two sides per match as one record → add a `recordFullMatch(home, away, homeGoals, awayGoals)` helper  
- Output must preserve input order of teams → use `asLinkedHashMapPreservingFirstSeen`  
- Output must be ranked → use `leaderboard()`
