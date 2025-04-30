package com.jpmc.midascore.entity;

import jakarta.persistence.*;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue()
    private long id;
    private float amount;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserRecord sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserRecord recipient;

    public TransactionRecord(float amount, UserRecord sender, UserRecord recipient) {
        this.amount = amount;
        this.sender = sender;
        this.recipient = recipient;
    }

    public float getAmount() {
        return amount;
    }

    public UserRecord getSender() {
        return sender;
    }

    public UserRecord getRecipient() {
        return recipient;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setSender(UserRecord sender) {
        this.sender = sender;
    }

    public void setRecipient(UserRecord recipient) {
        this.recipient = recipient;
    }
}
