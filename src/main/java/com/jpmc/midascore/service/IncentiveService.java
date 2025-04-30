package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IncentiveService {

    @Autowired
    RestTemplate restTemplate;

    private final String incentiveURL = "http://localhost:8080/incentive";
    public Incentive getIncentiveAmount(Transaction transaction){
        return restTemplate.postForObject(incentiveURL, transaction, Incentive.class);
    }
}
