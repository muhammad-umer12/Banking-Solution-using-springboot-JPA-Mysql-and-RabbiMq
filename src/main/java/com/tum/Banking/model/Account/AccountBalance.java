package com.tum.Banking.model.Account;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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
@Table(name = "account_balance")

public class AccountBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "customer_account_id")
    private String account_id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "currency_id", nullable = true)
    private Currency currency;

    @Column(name = "balance")
    private Double balance;

    public AccountBalance(AccountBalance ac)
    {
        this.account_id=ac.getAccount_id();
        this.id=ac.getId();
        this.balance=ac.getBalance();
        this.currency=ac.getCurrency();
    }

    public AccountBalance(String accountId,Currency currency,double balance)
    {
        this.account_id=accountId;
        this.currency=currency;
        this.balance=balance;
    }

}

