package com.jpmc.midascore.entity;

import com.jpmc.midascore.foundation.Balance;

import jakarta.persistence.*;

@Entity
public class UserRecord {

    @Id
    @GeneratedValue()
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Balance balance;

    protected UserRecord() {
    }

    public UserRecord(String name, float balance) {
        this.name = name;
        this.balance = new Balance(balance);
    }

    public UserRecord(String name, Balance balance) {
        this.name = name;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, name='%s', balance='%f'", id, name, balance.getAmount());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(float amount) {
        this.balance.setAmount(amount);
    }
}
