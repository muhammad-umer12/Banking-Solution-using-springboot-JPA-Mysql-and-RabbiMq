package com.tum.Banking.model.Account;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity

@Table(name = "transaction")

public class Transaction {

    @Id
    @Column(name = "id", nullable = false)
    private String id;


    @Column(name = "description")
    private String description;

    @Column(name = "currency")
    private String currency;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "balance")
    private double balance;

    @Column(name = "transaction_direction")
    private String transactionDirection;

    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;


    public Transaction(String id,String description, String transactionDirection, String currency,double amount,double balance,  String accountId) {
        this.id  = id;
        this.description = description;
        this.currency = currency;
        this.accountId = accountId;
        this.amount = amount;
        this.balance = balance;
        this.transactionDirection = transactionDirection;
    }
}
