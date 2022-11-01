package com.tum.Banking.repository.Account;

import com.tum.Banking.model.Account.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Integer> {

    @Query(value="SELECT ab.* FROM account_balance ab WHERE ab.customer_account_id =:accountId AND ab.currency_id=:currencyId limit 1",nativeQuery = true)
    AccountBalance findByAccountAndCurrencyId(@Param("accountId") String accountId, @Param("currencyId") int currencyId);


}