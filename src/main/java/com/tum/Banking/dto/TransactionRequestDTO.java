package com.tum.Banking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TransactionRequestDTO {

    private String description;
    private String transactionDirection;
    private double amount;
    private String currency;

    public TransactionRequestDTO(String description, String transactionDirection, double amount, String currency, String accountId) {
        this.description = description;
        this.transactionDirection = transactionDirection;
        this.amount = amount;
        this.currency = currency;
        this.accountId = accountId;
    }

    private String accountId;
}