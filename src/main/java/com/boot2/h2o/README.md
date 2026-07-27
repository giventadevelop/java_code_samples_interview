# H2O continuous pipeline (full version)

Package: `com.boot2.h2o`

| Class | Role |
|-------|------|
| `WaterFactory` | Semaphore + CyclicBarrier sync + `main` demo |
| `Hydrogen` / `Oxygen` | Continuous atom worker threads |
| `Water` | Domain model for one formed molecule |

Simplified interview-sized API: `com.boot2.simplified.h2o`

## Run

```text
mvn -q -DskipTests compile
java -cp target/classes com.boot2.h2o.WaterFactory
java -cp target/classes com.boot2.h2o.WaterFactory HOHHOH
```
