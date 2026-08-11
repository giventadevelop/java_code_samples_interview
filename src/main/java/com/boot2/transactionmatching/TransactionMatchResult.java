package com.boot2.transactionmatching;

import java.util.ArrayList;
import java.util.List;

/**
 * Result of comparing internal and partner transaction lists.
 */
public class TransactionMatchResult {

    private final List<Transaction> matched;
    private final List<Transaction> unmatchedInternal;
    private final List<Transaction> unmatchedPartner;
    private final List<Transaction> duplicateInternal;
    private final List<Transaction> duplicatePartner;

    public TransactionMatchResult(
            List<Transaction> matched,
            List<Transaction> unmatchedInternal,
            List<Transaction> unmatchedPartner,
            List<Transaction> duplicateInternal,
            List<Transaction> duplicatePartner) {
        this.matched = List.copyOf(matched);
        this.unmatchedInternal = List.copyOf(unmatchedInternal);
        this.unmatchedPartner = List.copyOf(unmatchedPartner);
        this.duplicateInternal = List.copyOf(duplicateInternal);
        this.duplicatePartner = List.copyOf(duplicatePartner);
    }

    public List<Transaction> getMatched() {
        return matched;
    }

    public List<Transaction> getUnmatchedInternal() {
        return unmatchedInternal;
    }

    public List<Transaction> getUnmatchedPartner() {
        return unmatchedPartner;
    }

    public List<Transaction> getDuplicateInternal() {
        return duplicateInternal;
    }

    public List<Transaction> getDuplicatePartner() {
        return duplicatePartner;
    }

    public List<Transaction> getAllUnmatched() {
        List<Transaction> all = new ArrayList<>(unmatchedInternal);
        all.addAll(unmatchedPartner);
        return List.copyOf(all);
    }

    public List<Transaction> getAllDuplicates() {
        List<Transaction> all = new ArrayList<>(duplicateInternal);
        all.addAll(duplicatePartner);
        return List.copyOf(all);
    }
}
