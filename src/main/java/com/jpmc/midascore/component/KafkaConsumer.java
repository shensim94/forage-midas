// Then update the KafkaConsumer.java to receive Transaction objects
package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private TransactionService transactionService;

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "my-group")
    public void consume(Transaction transaction) {
        //System.out.println("HELLO FROM KAFKA CONSUMER!!!!!!!!!!");
        //System.out.println("Received transaction: " + transaction);
        transactionService.processTransaction(transaction);
    }
}