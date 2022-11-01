package com.tum.Banking.repository.Account;

import com.tum.Banking.model.Account.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<CustomerAccount, Integer> {

    CustomerAccount findById(String id);

}
