package com.tum.Banking.service.Account;


import com.google.gson.Gson;
import com.tum.Banking.dto.AccountRequestDTO;
import com.tum.Banking.dto.AccountResponseDTO;
import com.tum.Banking.model.Account.AccountBalance;
import com.tum.Banking.model.Account.Currency;
import com.tum.Banking.model.Account.CustomerAccount;
import com.tum.Banking.repository.Account.AccountBalanceRepository;
import com.tum.Banking.repository.Account.AccountRepository;
import com.tum.Banking.repository.Account.CurrencyRepository;
import com.tum.Banking.service.Utility.UtilService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    AccountBalanceRepository accountBalanceRepository;

    @Autowired
    UtilService utilService;

    @Transactional
    public ResponseEntity<?> createAccount(AccountRequestDTO accountRequest)
    {

        String error="";
        try {

            if (!utilService.validateCurrency(accountRequest.getCurrency())) {
                error = "Invalid currency";
                throw new Exception(error);
            }
            UUID id = UUID.randomUUID();
            CustomerAccount account = new CustomerAccount();
            account.setCountry(accountRequest.getCountry());
            account.setCustomerId(accountRequest.getCustomerId());
            account.setId(generateId());
            CustomerAccount createdAccount = accountRepository.save(account);
            List<AccountBalance> balanceList = createBalances(accountRequest.getCurrency(), createdAccount.getId());
            AccountResponseDTO response = new AccountResponseDTO();
            response.setAccountId(createdAccount.getId());
            response.setCustomerId(accountRequest.getCustomerId());
            response.setBalances(balanceList);

            Gson gson = new Gson();
            String result = gson.toJson(response);
            //    return new ResponseEntity<String>(result, HttpStatus.OK);
            return new ResponseEntity<String>(
                    new JSONObject("{'success':  true,'data':" + result + "  }").toString(), HttpStatus.OK
            );
        }
        catch(Exception e)
        {
            return new ResponseEntity<String>(
                    new JSONObject("{'success':  false,'data':" + e.getMessage() + "  }").toString(), HttpStatus.BAD_REQUEST
            );
        }
    }

    public String generateId()
    {
        UUID id = UUID.randomUUID();
        CustomerAccount account = accountRepository.findById(id.toString());

        while(account!= null)
        {
            id = UUID.randomUUID();
            account = accountRepository.findById(id.toString());
        }

        return id.toString();

    }

    @Transactional
    public List<AccountBalance> createBalances(List<String> currencies, String accountId)
    {
        List<Currency> currencyList = currencyRepository.findCurrencies(currencies);
        List<AccountBalance> accountBalances = new ArrayList<>();

        for(Currency currency: currencyList)
        {
            AccountBalance balance = new AccountBalance();
            balance.setCurrency(currency);
            balance.setBalance(0.00);
            balance.setAccount_id(accountId);
            accountBalances.add(balance);

        }
        List<AccountBalance> createdBalances =  accountBalanceRepository.saveAll(accountBalances);
        return createdBalances;
    }

    public  ResponseEntity<?> getAccountById(String accountId)
    {
        try{
            String error="";
            CustomerAccount customerAccount = accountRepository.findById(accountId);
            if(customerAccount==null)
            {
                error = "Accont does not exit os given id "+accountId;
            }


            AccountResponseDTO response = new AccountResponseDTO();
            response.setCustomerId(customerAccount.getCustomerId());
            response.setAccountId(customerAccount.getId());
            response.setBalances(customerAccount.getBalance());
            Gson gson = new Gson();
            String result = gson.toJson(response);
            return new ResponseEntity<String>(
                    new JSONObject("{'success':  true,'data':" + result + "  }").toString(), HttpStatus.OK
            );
        }
        catch(Exception e)
        {
            return new ResponseEntity<String>(
                    new JSONObject("{'success':  false,'data':" + e.getMessage() + "  }").toString(), HttpStatus.BAD_REQUEST
            );
        }
    }

    public void populateCurrencyTable()
    {
        List<Currency> currencies = currencyRepository.findAll();

        if(currencies.size()==0)
        {
            List<Currency> currencyList = new ArrayList<>();
            currencyList.add(new Currency("EUR"));
            currencyList.add(new Currency("USD"));
            currencyList.add(new Currency("GBP"));
            currencyList.add(new Currency("SEK"));
            currencyRepository.saveAll(currencyList);

        }
    }




}