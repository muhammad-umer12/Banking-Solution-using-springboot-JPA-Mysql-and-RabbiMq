package com.tum.Banking.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransactionResponseDTO {

    private String description;
    private String transactionDirection;
    private double amount;
    private String currency;
    private String accountId;
    private String transactionId;
    private double balance;

}
