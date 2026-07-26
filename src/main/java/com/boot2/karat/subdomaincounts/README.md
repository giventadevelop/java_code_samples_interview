# Karat / Indeed: Subdomain Count Aggregation

Package: `com.boot2.karat.subdomaincounts`

Practice reconstruction of a Karat coding screen reported in an Indeed interview experience:

https://leetcode.com/discuss/interview-experience/1713353/karat-interview-experience-indeed

---

## Problem

**Given** a map of domain → count:

```text
google.com  -> 900
a.b.c.com   -> 20
b.c.com     -> 30
c.com       -> 10
```

**Return** aggregate counts for each domain **and every parent subdomain**.

A hit on `a.b.c.com` also counts toward `b.c.com`, `c.com`, and `com`.

### Full roll-up (intended solution)

| Domain | Count | Why |
|--------|------:|-----|
| `a.b.c.com` | 20 | input only |
| `b.c.com` | 50 | 20 (from `a.b.c.com`) + 30 |
| `c.com` | 60 | 20 + 30 + 10 |
| `google.com` | 900 | input only |
| `com` | 960 | 900 + 20 + 30 + 10 |

### About the forum’s sample output

Some posts showed:

```text
google.com->900, a.b.c.com->20, b.c.com->30, c.com->10, com->960
```

That only adds the TLD `com`. In the real “form all sub-domains” task (and LeetCode 811),
parent domains like `b.c.com` / `c.com` are **also** aggregated. This package implements
the full version interviewers usually want.

Same family as: **LeetCode 811 — Subdomain Visit Count** (input format may be
`"900 google.com"` strings instead of a `Map`).

---

## What type of question is this?

| Aspect | Detail |
|--------|--------|
| Style | Karat OA — string parsing + map aggregation |
| Difficulty | Easy (gotcha makes it feel harder under pressure) |
| Skills | `Map.merge`, suffix generation, regex-aware `split` |
| Famous trap | `String.split(".")` vs `String.split("\\.")` |

The Indeed write-up said this took ~33 minutes mainly because of forming subdomains
and the split regex tip from the interviewer.

---

## The Java gotcha (memorize this)

```java
"a.b.c.com".split(".")     // WRONG — "." is regex "any character"
"a.b.c.com".split("\\.")   // RIGHT — literal '.'
```

`String.split(String regex)` always takes a **regular expression**.

---

## Algorithm (interview talking points)

1. For each `(domain, count)` in the input map  
2. Split labels with `domain.split("\\.")`  
3. Build every suffix: `a.b.c.com`, `b.c.com`, `c.com`, `com`  
4. `result.merge(suffix, count, Integer::sum)`  
5. Return the result map  

Time: O(N · L²) string work in the naive join approach (N domains, L labels) — fine for interview sizes.  
Space: O(number of distinct suffixes).

---

## Classes

| Class | Role |
|-------|------|
| `SubdomainCounter` | `expandSubdomains` + `aggregate` / `aggregateSorted` |
| `SubdomainCountDemo` | Sample input, split gotcha demo, assertions |
| `package-info.java` | Short package Javadoc |

---

## How to run

```text
mvn -q -DskipTests compile
java -cp target/classes com.boot2.karat.subdomaincounts.SubdomainCountDemo
```

---

## Related package

Match results / admin approve-reject / HashMap ordering:

`com.boot2.karat.matchresults`
