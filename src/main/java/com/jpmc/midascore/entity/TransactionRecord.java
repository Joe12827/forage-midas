package com.jpmc.midascore.entity;

import jakarta.persistence.*;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue()
    private long id;

    @Column(nullable = false)
    private long sender_Id;

    @Column(nullable = false)
    private long recipient_Id;

    @Column(nullable = false)
    private float amount;

    protected TransactionRecord() {
    }

    public TransactionRecord(long senderId, long recipientId, float amount) {
        this.sender_Id = senderId;
        this.recipient_Id = recipientId;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("transaction[id=%d, senderId='%d', recipientId='%d', amount='%f'", id, sender_Id, recipient_Id, amount);
    }

    public Long getId() {
        return id;
    }

    public long getSenderId() {
        return sender_Id;
    }

    public long getRecipientId() {
        return recipient_Id;
    }
}
