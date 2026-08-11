package com.boot2.transactionmatching;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A transaction used for internal vs partner reconciliation.
 * Equality is based on {@code id} and {@code amount} (business match key).
 */
public class Transaction {

    private final int id;
    private final double amount;
    private final LocalDateTime timeStamp;

    public Transaction(int id, double amount, LocalDateTime timeStamp) {
        this.id = id;
        this.amount = amount;
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction that)) {
            return false;
        }
        return id == that.id && Double.compare(that.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }

    @Override
    public String toString() {
        return "Transaction{id=" + id + ", amount=" + amount + ", timeStamp=" + timeStamp + '}';
    }
}
