package com.boot2.transactionmatching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Compares internal and partner transaction lists and reports:
 * <ul>
 *   <li>matched records present in both lists</li>
 *   <li>unmatched / distinct records with no counterpart</li>
 *   <li>duplicated records within each list</li>
 * </ul>
 */
public class TransactionMatcher {

    public TransactionMatchResult match(List<Transaction> internalTxns, List<Transaction> partnerTxns) {
        List<Transaction> internal = internalTxns == null ? List.of() : internalTxns;
        List<Transaction> partner = partnerTxns == null ? List.of() : partnerTxns;

        List<Transaction> duplicateInternal = findDuplicates(internal);
        List<Transaction> duplicatePartner = findDuplicates(partner);

        Set<Transaction> internalDistinct = new HashSet<>(internal);
        Set<Transaction> partnerDistinct = new HashSet<>(partner);

        List<Transaction> matched = new ArrayList<>();
        List<Transaction> unmatchedInternal = new ArrayList<>();
        List<Transaction> unmatchedPartner = new ArrayList<>();

        for (Transaction txn : internalDistinct) {
            if (partnerDistinct.contains(txn)) {
                matched.add(txn);
            } else {
                unmatchedInternal.add(txn);
            }
        }

        for (Transaction txn : partnerDistinct) {
            if (!internalDistinct.contains(txn)) {
                unmatchedPartner.add(txn);
            }
        }

        return new TransactionMatchResult(
                matched,
                unmatchedInternal,
                unmatchedPartner,
                duplicateInternal,
                duplicatePartner);
    }

    /**
     * Returns one representative for each transaction key that appears more than once.
     */
    private List<Transaction> findDuplicates(List<Transaction> txns) {
        Map<Transaction, Integer> counts = new HashMap<>();
        for (Transaction txn : txns) {
            counts.merge(txn, 1, Integer::sum);
        }

        List<Transaction> duplicates = new ArrayList<>();
        for (Map.Entry<Transaction, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > 1) {
                duplicates.add(entry.getKey());
            }
        }
        return duplicates;
    }
}
