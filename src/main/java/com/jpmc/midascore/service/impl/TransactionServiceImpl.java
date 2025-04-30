package com.jpmc.midascore.service.impl;

import com.jpmc.midascore.entity.Incentive;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.MyUserRepository;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.service.IncentiveService;
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

    @Autowired
    private IncentiveService incentiveService;

    @Override
    @Transactional
    public void processTransaction(Transaction transaction) {

        // validate transaction
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());
        if (sender == null || recipient == null) return;
        if (sender.getBalance() < transaction.getAmount()) return;

        // post to incentive API
        Incentive incentive = incentiveService.getIncentiveAmount(transaction);


        // update user balance and apply incentive amount
        sender.setBalance(sender.getBalance() - transaction.getAmount());
        recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentive.getAmount());

        TransactionRecord transactionRecord = new TransactionRecord(transaction.getAmount(), sender, recipient);

        System.out.println("sender: "+sender);
        System.out.println("recipient: "+recipient);
        transactionRepository.save(transactionRecord);
        userRepository.save(sender);
        userRepository.save(recipient);

    }
}
