package com.tum.Banking.dto;

import com.tum.Banking.model.Account.AccountBalance;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountResponseDTO {
    private  int customerId;
    private String accountId;
    private List<AccountBalance> balances;
}
