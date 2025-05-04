package com.boot2;

import java.util.*;

// We assume that these classes are provided in the locked stub
enum TransactionType {
    P2P,    // Peer-to-peer
    P2M,    // Peer-to-merchant
    SELF    // Self transaction
}

class TransactionSummary {
    private int transactionId;
    private boolean isSenderEligibleForReward;
    
    public TransactionSummary(int transactionId, boolean isSenderEligibleForReward) {
        this.transactionId = transactionId;
        this.isSenderEligibleForReward = isSenderEligibleForReward;
    }
    
    // Getters would be provided in the locked stub
    
    @Override
    public String toString() {
        return transactionId + " " + isSenderEligibleForReward;
    }
}

public class Payment {
    // Store transactions for each user by transaction type
    private Map<Integer, Map<TransactionType, List<Integer>>> userTransactions = new HashMap<>();
    
    // Track total P2M transaction amount for each user
    private Map<Integer, Integer> p2mAmountByUser = new HashMap<>();
    
    /**
     * Makes a payment and determines if the sender is eligible for a reward
     * 
     * @param transactionId the unique ID of the transaction
     * @param senderId the ID of the user making the payment
     * @param amount the amount of the transaction
     * @param transactionType the type of transaction (P2P, P2M, or SELF)
     * @return a TransactionSummary with transaction ID and reward eligibility
     */
    public TransactionSummary makePayment(int transactionId, int senderId, int amount, TransactionType transactionType) {
        // Record the transaction for the user
        userTransactions.putIfAbsent(senderId, new HashMap<>());
        userTransactions.get(senderId).putIfAbsent(transactionType, new ArrayList<>());
        userTransactions.get(senderId).get(transactionType).add(transactionId);
        
        boolean isEligibleForReward = false;
        
        // Only P2M transactions can be eligible for rewards
        if (transactionType == TransactionType.P2M) {
            // Update the user's total P2M amount
            int newTotal = p2mAmountByUser.getOrDefault(senderId, 0) + amount;
            p2mAmountByUser.put(senderId, newTotal);
            
            // Check if user is in top 100 by P2M transaction amount
            isEligibleForReward = isInTop100ByP2MAmount(senderId);
        }
        
        return new TransactionSummary(transactionId, isEligibleForReward);
    }
    
    /**
     * Gets the number of transactions of a specific type made by a user
     * 
     * @param senderId the ID of the user
     * @param transactionType the type of transactions to count
     * @return the number of transactions of the specified type
     */
    public int getNumberOfTransactions(int senderId, TransactionType transactionType) {
        if (!userTransactions.containsKey(senderId)) {
            return 0;
        }
        
        Map<TransactionType, List<Integer>> transactions = userTransactions.get(senderId);
        if (!transactions.containsKey(transactionType)) {
            return 0;
        }
        
        return transactions.get(transactionType).size();
    }
    
    /**
     * Helper method to determine if a user is in the top 100
     * users by total P2M transaction amount
     * 
     * @param senderId the ID of the user to check
     * @return true if the user is in the top 100, false otherwise
     */
    private boolean isInTop100ByP2MAmount(int senderId) {
        // If we have 100 or fewer users with P2M transactions,
        // all users are in the top 100
        if (p2mAmountByUser.size() <= 100) {
            return true;
        }
        
        // Get the current user's P2M amount
        int userAmount = p2mAmountByUser.get(senderId);
        
        // Create a list of all P2M amounts
        List<Integer> allAmounts = new ArrayList<>(p2mAmountByUser.values());
        
        // Sort in descending order
        Collections.sort(allAmounts, Collections.reverseOrder());
        
        // Check if the user's amount is in the top 100
        int position = 0;
        for (int amount : allAmounts) {
            position++;
            if (amount == userAmount) {
                return position <= 100;
            }
            
            // Early exit if we've passed position 100
            if (position >= 100) {
                return false;
            }
        }
        
        return false;
    }

    public static void main(String[] args) {
//        makePayment 0 2 100 P2P
    }
}
