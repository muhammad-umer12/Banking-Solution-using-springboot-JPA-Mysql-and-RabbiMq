package com.tum.Banking.repository.Account;

import com.tum.Banking.model.Account.AccountBalance;
import com.tum.Banking.model.Account.Currency;
import com.tum.Banking.model.Account.CustomerAccount;
import com.tum.Banking.model.Account.Transaction;
import com.tum.Banking.model.Customer.Customer;
import com.tum.Banking.repository.Customer.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class TransactionRepositoryTest {

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountBalanceRepository accountBalanceRepository;

    @Autowired
    TransactionRepository transactionRepository;



    @Test
    void findById() {

        Currency currency = new Currency("EUR");
        Currency currencyResult = currencyRepository.save(currency);

        Customer customer = new Customer();
        customer.setContact("03308548034");
        customer.setName("Umer");
        customer.setEmail("m.umer@gmail.com");

        Customer result = customerRepository.save(customer);

        String id = UUID.randomUUID().toString();
        CustomerAccount account = new CustomerAccount();

        account.setCustomerId(result.getId());
        account.setCountry("Ireland");
        account.setId(id);
        accountRepository.save(account);

        AccountBalance accountBalance = new AccountBalance(id,currencyResult,0);
        AccountBalance accountBalanceResult =accountBalanceRepository.save(accountBalance);

        String teransactionId = UUID.randomUUID().toString();
        Transaction transaction = new Transaction(teransactionId,"add","IN","EUR",1000,1000,id);
        Transaction expected = transactionRepository.save(transaction);
        Transaction actual = transactionRepository.findById(teransactionId);

        assertEquals(actual.getId(),expected.getId());
    }

    @Test
    void getTransactionByAccount() {

        Currency currency = new Currency("EUR");
        Currency currencyResult = currencyRepository.save(currency);

        Customer customer = new Customer();
        customer.setContact("03308548034");
        customer.setName("Umer");
        customer.setEmail("m.umer@gmail.com");

        Customer result = customerRepository.save(customer);

        String id = UUID.randomUUID().toString();
        CustomerAccount account = new CustomerAccount();

        account.setCustomerId(result.getId());
        account.setCountry("Ireland");
        account.setId(id);
        accountRepository.save(account);

        AccountBalance accountBalance = new AccountBalance(id,currencyResult,0);
        AccountBalance accountBalanceResult =accountBalanceRepository.save(accountBalance);

        String teransactionId = UUID.randomUUID().toString();
        Transaction transaction = new Transaction(teransactionId,"add","IN","EUR",1000,1000,id);

        Transaction expected = transactionRepository.save(transaction);

        List<Transaction> actual = transactionRepository.getTransactionByAccount(id);
        List<Transaction> temp = new ArrayList<>();
        temp.add(expected);
        assertEquals(actual.size(),temp.size());


    }

    @AfterEach
    public void removeData()
    {
        accountBalanceRepository.deleteAll();
        accountRepository.deleteAll();
        customerRepository.deleteAll();
        currencyRepository.deleteAll();
        transactionRepository.deleteAll();


    }
}