package com.jpmc.midascore.controller;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserRecordController {

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/balance")
    public Balance getBalance(@RequestParam(required = true) Long userId){
        Optional<UserRecord> userRecord = userRepository.findById(userId);
        return userRecord.map(record -> new Balance(record.getBalance())).orElseGet(() -> new Balance(0));
    }
}
