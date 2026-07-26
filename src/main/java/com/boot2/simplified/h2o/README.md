# Simplified H2O / Building Water

Package: `com.boot2.simplified.h2o`

Short interview form of the classic concurrency problem.  
Full continuous-pipeline version (workers, molecule counting, longer comments):  
`com.boot2.WaterFactory` (+ `Hydrogen` / `Oxygen` / `Water`).

---

## Problem

Threads release **H** or **O**. Let them proceed only in groups of **2 hydrogen + 1 oxygen**.

## Idea

1. Cap how many H and O enter the reaction chamber (`Semaphore`).
2. Wait until you have a full set `(H, H, O)` (`CyclicBarrier` size 3).
3. Release them together as one water molecule.

Common tools: **Semaphore + CyclicBarrier** (alternatives: CountDownLatch / Lock + Condition).

## Implementation core

```java
private final Semaphore hSem = new Semaphore(2); // max 2 H
private final Semaphore oSem = new Semaphore(1); // max 1 O
private final CyclicBarrier barrier = new CyclicBarrier(3);

public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
    hSem.acquire();
    try {
        barrier.await();
        releaseHydrogen.run();
    } finally {
        hSem.release();
    }
}

public void oxygen(Runnable releaseOxygen) throws InterruptedException {
    oSem.acquire();
    try {
        barrier.await();
        releaseOxygen.run();
    } finally {
        oSem.release();
    }
}
```

| Piece | Role |
|--------|------|
| `hSem(2)` | Never more than 2 H waiting |
| `oSem(1)` | Never more than 1 O waiting |
| `CyclicBarrier(3)` | Exactly 2H+1O pass together; then barrier resets |

## Run

```text
mvn -q -DskipTests compile
java -cp target/classes com.boot2.simplified.h2o.H2ODemo
java -cp target/classes com.boot2.simplified.h2o.H2ODemo HOHHOH
```
