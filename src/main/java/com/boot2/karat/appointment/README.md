# Clinic Appointment Manager — Solution Notes

Package: `com.boot2.karat.appointment`

Karat-style interview: doctors, appointments, clinic statistics, and average
duration by appointment type.

## What was wrong with `AppointMentTest.java`

The original file did not compile / structure cleanly:

1. An `import` appeared **in the middle of the class body** (illegal in Java).
2. Enums and domain classes were nested incorrectly inside `AppointMentTest`.
3. `Solution` was a nested class with static tests that depended on that broken layout.

## Bugs in `getAverageAppointmentDurationByType`

| Bug | Symptom | Fix |
|---|---|---|
| Integer division `duration / count` | `(30+41)/2` → `35` instead of `35.5` | `(double) duration / count` |
| Always `put` every `AppointmentType` | Map contained empty types with `0.0` | Put only when `count > 0` |
| No unknown-doctor check | Doctor `999` returned zeros, not empty | Return empty map if doctor missing |

Also only **COMPLETED** appointments count; `CANCELLED` / `SCHEDULED` / `NO_SHOW` are ignored.

## Correct logic (summary)

```text
if doctorId not registered → return {}
for each COMPLETED appointment belonging to that doctor:
    accumulate minutes and count per AppointmentType
for each type with count > 0:
    average = totalMinutes / count   (floating-point)
return averages
```

## Worked example (doctor 1)

| Appt | Duration | Status | Type | Counted? |
|---|---|---|---|---|
| 1 | 30 | COMPLETED | CONSULTATION | yes |
| 2 | 41 | COMPLETED | CONSULTATION | yes |
| 3 | 20 | COMPLETED | FOLLOWUP | yes |
| 5 | 100 | CANCELLED | CONSULTATION | no |

- CONSULTATION → `(30 + 41) / 2 = 35.5`
- FOLLOWUP → `20.0`
- EMERGENCY → omitted

## Classes in this package

| Class | Role |
|---|---|
| `AppointmentStatus` / `AppointmentType` | Enums |
| `Doctor`, `Appointment`, `AppointmentStats` | Model |
| `ClinicManager` | Fixed business logic |
| `AppointMentTest` | Task 1 & 2 assertions |

## Run

```bash
mvn -q compile
java -cp target/classes com.boot2.karat.appointment.AppointMentTest
```

Expected: `All tests pass!`
