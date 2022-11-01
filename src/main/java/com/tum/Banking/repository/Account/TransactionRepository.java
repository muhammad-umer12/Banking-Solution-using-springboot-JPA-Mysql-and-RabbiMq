package com.tum.Banking.repository.Account;

import com.tum.Banking.model.Account.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findById(String id);

    @Query(value = "SELECT t.* from  `transaction` t where account_id =:accountId",nativeQuery = true)
    List<Transaction> getTransactionByAccount(@Param("accountId") String accountId);

}