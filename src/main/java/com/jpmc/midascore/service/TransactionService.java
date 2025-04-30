package com.jpmc.midascore.service;

public interface TransactionService {
    void processTransaction(long senderId, long recipientId, float amount);
}
