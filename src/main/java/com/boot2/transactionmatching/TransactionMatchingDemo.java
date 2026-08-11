package com.boot2.transactionmatching;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionMatchingDemo {

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.of(2026, 7, 30, 10, 0);

        List<Transaction> internalTxns = new ArrayList<>();
        internalTxns.add(new Transaction(1, 100.0, now));
        internalTxns.add(new Transaction(2, 200.0, now));
        internalTxns.add(new Transaction(3, 300.0, now));
        internalTxns.add(new Transaction(2, 200.0, now)); // duplicate in internal

        List<Transaction> partnerTxns = new ArrayList<>();
        partnerTxns.add(new Transaction(1, 100.0, now));  // matches internal #1
        partnerTxns.add(new Transaction(2, 200.0, now));  // matches internal #2
        partnerTxns.add(new Transaction(4, 400.0, now));  // partner-only
        partnerTxns.add(new Transaction(4, 400.0, now));  // duplicate in partner

        TransactionMatcher matcher = new TransactionMatcher();
        TransactionMatchResult result = matcher.match(internalTxns, partnerTxns);

        System.out.println("Matched:");
        result.getMatched().forEach(System.out::println);

        System.out.println("\nUnmatched (internal only):");
        result.getUnmatchedInternal().forEach(System.out::println);

        System.out.println("\nUnmatched (partner only):");
        result.getUnmatchedPartner().forEach(System.out::println);

        System.out.println("\nDuplicates (internal):");
        result.getDuplicateInternal().forEach(System.out::println);

        System.out.println("\nDuplicates (partner):");
        result.getDuplicatePartner().forEach(System.out::println);
    }
}
