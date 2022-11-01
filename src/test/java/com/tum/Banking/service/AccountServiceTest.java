package com.tum.Banking.service;

import com.tum.Banking.dto.AccountRequestDTO;
import com.tum.Banking.model.Account.Currency;
import com.tum.Banking.model.Customer.Customer;
import com.tum.Banking.repository.Account.AccountBalanceRepository;
import com.tum.Banking.repository.Account.AccountRepository;
import com.tum.Banking.repository.Account.CurrencyRepository;
import com.tum.Banking.repository.Customer.CustomerRepository;
import com.tum.Banking.service.Account.AccountService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AccountServiceTest {

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

    @Test
    void createAccount() {

        Currency currencyResult = currencyRepository.save(new Currency("EUR"));
        Customer customer = new Customer();
        customer.setContact("03308548034");
        customer.setName("Umer");
        customer.setEmail("m.umer@gmail.com");

        Customer result = customerRepository.save(customer);

        List<String> str = new ArrayList<>();
        str.add("EUR");
        AccountRequestDTO request = new AccountRequestDTO();
        request.setCountry("Ireland");
        request.setCustomerId(result.getId());
        request.setCurrency(str);


        ResponseEntity<?> expected = new ResponseEntity<String>(
                new JSONObject("{'success':  true,'data':" + request + "  }").toString(), HttpStatus.OK
        );
        assertEquals(expected.toString().contains("data"),accountService.createAccount(request).toString().contains("data"));
    }

    @Test
    void generateId() {

        String a =  accountService.generateId();
        assertEquals(a.length(), UUID.randomUUID().toString().length());

    }

    @Test
    void createBalances() {


    }

    @AfterEach
    public void removeData()
    {
        accountBalanceRepository.deleteAll();
        accountRepository.deleteAll();
        customerRepository.deleteAll();
        currencyRepository.deleteAll();

    }
}