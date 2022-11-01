package com.tum.Banking.service;

import com.tum.Banking.dto.TransactionRequestDTO;
import com.tum.Banking.model.Account.AccountBalance;
import com.tum.Banking.model.Account.Currency;
import com.tum.Banking.model.Account.CustomerAccount;
import com.tum.Banking.model.Account.Transaction;
import com.tum.Banking.model.Customer.Customer;
import com.tum.Banking.repository.Account.AccountBalanceRepository;
import com.tum.Banking.repository.Account.AccountRepository;
import com.tum.Banking.repository.Account.CurrencyRepository;
import com.tum.Banking.repository.Account.TransactionRepository;
import com.tum.Banking.repository.Customer.CustomerRepository;
import com.tum.Banking.service.Account.AccountService;
import com.tum.Banking.service.Account.TransactionService;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class TransactionServiceTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountBalanceRepository accountBalanceRepository;

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    void createTransaction() {


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

        String transactionId = UUID.randomUUID().toString();
        Transaction transaction = new Transaction(transactionId,"add","IN","EUR",1000,1000,id);

        TransactionRequestDTO request = new TransactionRequestDTO("add","IN",1000,"EUR",id);


        ResponseEntity<?>  expect = new ResponseEntity<String>(
                new JSONObject("{'success':  true,'data':" + request + "  }").toString(), HttpStatus.OK
        );

        assertEquals(expect.toString().contains("data"),transactionService.createTransaction(request).toString().contains("data"));

    }

    @AfterEach
    public void removeData()
    {
        transactionRepository.deleteAll();
        accountBalanceRepository.deleteAll();
        accountRepository.deleteAll();
        customerRepository.deleteAll();
        currencyRepository.deleteAll();

    }

    @Test
    void generateId() {

        String a =  accountService.generateId();
        assertEquals(a.length(), UUID.randomUUID().toString().length());

    }
    @Test
    void addTransaction() {


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

        TransactionRequestDTO request = new TransactionRequestDTO("add","IN",1000,"EUR",id);

        assertEquals(50,transactionService.addTransaction(request,50).getBalance());

    }

    @Test
    void getAllTransaction()
    {
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

        TransactionRequestDTO request = new TransactionRequestDTO("add","IN",1000,"EUR",id);

        ResponseEntity<?>  expect = new ResponseEntity<String>(
                new JSONObject("{'success':  true,'data':" + request + "  }").toString(), HttpStatus.OK
        );

        assertEquals(expect.toString().contains("data"),transactionService.getAllTransactions(id).toString().contains("data"));
    }
}