package com.jpmc.midascore.service.impl;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.repository.MyUserRepository;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.service.TransactionService;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MyUserRepository userRepository;

    @Override
    @Transactional
    public void processTransaction(long senderId, long recipientId, float amount) {
        UserRecord sender = userRepository.findById(senderId);
        UserRecord recipient = userRepository.findById(recipientId);
        if (sender == null || recipient == null) return;
        if (sender.getBalance() < amount) return;

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        TransactionRecord transactionRecord = new TransactionRecord(amount, sender, recipient);

        System.out.println("sender: "+sender);
        System.out.println("recipient: "+recipient);
        transactionRepository.save(transactionRecord);
        userRepository.save(sender);
        userRepository.save(recipient);

    }
}
