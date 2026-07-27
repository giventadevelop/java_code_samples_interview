# MatchResultsDemo — `main` walkthrough (plain English)

Separate from `README.md`. This document explains **only** what `MatchResultsDemo.main` does,
step by step: what each class is for, and what each important method call means in simple words.

Source: `MatchResultsDemo.java`

---

## Classes you meet in `main` (quick map)

| Class / type | What it is (plain English) |
|--------------|----------------------------|
| `MatchOutcome` | Win, draw, or loss — and how many points each is worth (3 / 1 / 0). |
| `MatchResult` | One team’s result for one match (who played, win/draw/loss, points). |
| `TeamStanding` | Running totals for one team: wins, draws, losses, total points, average points. |
| `LeagueTable` | The scoreboard builder: feeds in many `MatchResult`s and builds standings. |
| `MatchAdministrator` | The “referee admin”: looks at a standing and says APPROVED or REJECTED. |
| `AdminDecision` | Just the two answers: APPROVED or REJECTED. |
| `EvaluationResult` | Admin’s full answer: team name + decision + reason text. |

---

## Step-by-step through `main`

### Opening

```java
System.out.println("=== Karat-style Match Results Demo ===");
```

Prints a title so you know the demo started. No business logic.

---

### Step A — Build the sample match list

```java
List<MatchResult> results = sampleResults();
```

**What happens:** Calls `sampleResults()`, which creates a list of made-up match rows
(Alpha, Bravo, Charlie, Delta with various wins/draws/losses).

**What each `new MatchResult(...)` does:**  
Creates one row like “Alpha won match M1” (and stores the 3 points for a win automatically
via `MatchOutcome.WIN`).

**Why:** This is the input data a Karat question would give you (or you’d parse from a file).

---

### Step B — Print the raw input

```java
printRawResults(results);
```

**What happens:** Loops over every `MatchResult` and prints it.

**Plain English:** “Show me the inbox of match results before we score anyone.”  
Useful so you can compare input vs later standings.

---

### Step C — Create an empty league table and load all results

```java
LeagueTable table = new LeagueTable();
table.recordAll(results);
```

**`new LeagueTable()`**  
Makes an empty scoreboard (inside it, a `HashMap` from team name → `TeamStanding`).

**`table.recordAll(results)`**  
For each match result in the list, call `record(...)`:

- Find the team’s standing (or create one if this is the first time we see that team).
- Call `TeamStanding.apply(result)` to add one more win/draw/loss and add the points.

**Plain English:** “Feed every match into the scoreboard so each team has updated totals.”

---

### Step D — Show HashMap order (the trap)

```java
Map<String, TeamStanding> hashView = table.asHashMap();
for (Map.Entry<String, TeamStanding> e : hashView.entrySet()) {
    System.out.println("  " + e.getValue());
}
```

**`asHashMap()`**  
Hands you the internal map of team → standing. Order is **not** guaranteed.

**The loop**  
Prints each team’s standing in whatever order the `HashMap` happens to give.

**Plain English:** “If you print a HashMap and call it a ranking, you’re wrong — the order can look random.”  
This step exists to teach that interview gotcha.

---

### Step E — Show first-seen order (LinkedHashMap)

```java
LinkedHashMap<String, TeamStanding> linked =
    table.asLinkedHashMapPreservingFirstSeen(results);
for (TeamStanding s : linked.values()) {
    System.out.println("  " + s);
}
```

**`asLinkedHashMapPreservingFirstSeen(results)`**  
Builds a map that lists teams in the order they **first appeared** in the input list
(Alpha, then Bravo, then Charlie, then Delta).

**The loop**  
Prints standings in that processing order.

**Plain English:** “If the interviewer wants ‘order we first saw each team,’ use LinkedHashMap-style order — not a raw HashMap.”

---

### Step F — Build the real leaderboard (preferred ranking)

```java
List<TeamStanding> board = table.leaderboard();
int rank = 1;
for (TeamStanding s : board) {
    System.out.println("  #" + rank++ + "  " + s);
}
```

**`table.leaderboard()`**  
Takes all team standings, puts them in a list, and **sorts** them by:

1. most total points first  
2. then higher average points  
3. then team name A→Z if still tied  

**The loop**  
Prints `#1`, `#2`, … next to each standing.

**Plain English:** “This is the proper ranked table you’d hand in as the answer.”

When you print `s` (a `TeamStanding`), its `toString()` shows wins, draws, losses, points, average, and matches played.

---

### Step G — Administrator reviews each team

```java
MatchAdministrator admin = MatchAdministrator.defaultPolicy();
for (TeamStanding s : board) {
    MatchAdministrator.EvaluationResult eval = admin.evaluate(s);
    System.out.println("  " + eval);
}
```

**`MatchAdministrator.defaultPolicy()`**  
Creates an admin with default rules, for example:

- need at least 2 matches  
- average points at least 1.0  
- wins should not be less than losses  

**`admin.evaluate(s)`**  
Looks at one team’s standing and returns APPROVED or REJECTED plus a reason.

**Plain English:** “For each team on the leaderboard, ask the admin: pass or fail, and why?”

---

### Step H — Print the human-readable expected answers

```java
System.out.println("--- 5) Expected evaluation snapshot ---");
// ... Alpha APPROVED, Charlie REJECTED, etc.
```

**What happens:** Only prints comments of what we *expect* — does not calculate anything new.

**Plain English:** “Cheat sheet so you can eyeball the console output.”

---

### Step I — Self-check (assertions)

```java
assertDemoExpectations(table, admin);
System.out.println("Demo assertions passed.");
```

**`assertDemoExpectations(...)`** (in plain English):

| Call | Meaning |
|------|---------|
| `table.getStanding("Alpha")` | Fetch Alpha’s totals from the scoreboard. |
| `alpha.getTotalPoints()` | Read Alpha’s point total; must be 7. |
| `admin.evaluate(alpha).isApproved()` | Admin must say yes for Alpha. |
| `admin.evaluate(charlie).isApproved()` | Must be **false** (Charlie should fail). |
| `admin.evaluate(delta).isApproved()` | Must be **false** (Delta only played once). |
| `table.leaderboard()` again | First place must be Alpha. |
| Small `HashMap` size check | Just proves a HashMap can hold 3 keys; order is ignored on purpose. |

If any check fails, the demo throws an error. If all pass, it prints “Demo assertions passed.”

---

## Helpers used by `main` (not inside `main`, but called from it)

### `sampleResults()`

Builds the fake season: many `new MatchResult(matchId, teamName, outcome)` rows, returned as a `List`.

### `printRawResults(results)`

Prints every input `MatchResult` using its `toString()`.

### `assertDemoExpectations(table, admin)`

Automatic tests so a broken change to scoring/admin rules fails loudly.

---

## End-to-end story (one paragraph)

`main` builds a list of match results, prints them, feeds them into a `LeagueTable` so each team gets a `TeamStanding`, shows why HashMap order is unsafe, shows first-seen order, builds a sorted leaderboard, has a `MatchAdministrator` approve or reject each team, prints what we expect, then runs assertions to prove Alpha is approved and first, while Charlie and Delta are rejected.

---

## How to run this demo

```text
mvn -q -DskipTests compile
java -cp target/classes com.boot2.karat.matchresults.MatchResultsDemo
```

For the problem overview and HashMap tips, see `README.md` in this same package.
