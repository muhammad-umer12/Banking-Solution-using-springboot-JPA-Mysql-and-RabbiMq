package com.tum.Banking.service;

import com.tum.Banking.dto.CustomerRequestDTO;
import com.tum.Banking.repository.Account.AccountRepository;
import com.tum.Banking.repository.Account.CurrencyRepository;
import com.tum.Banking.repository.Customer.CustomerRepository;
import com.tum.Banking.service.Customer.CustomerService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CustomerServiceTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CurrencyRepository currencyRepository;



    @Test
    void createCustomer() {



        CustomerRequestDTO request = new CustomerRequestDTO();
        request.setContact("03308548034");
        request.setName("umer");
        request.setEmail("umer@gmail.com");

        ResponseEntity<?> expected = new ResponseEntity<String>(
                new JSONObject("{'success':  true,'data':" + request + "  }").toString(), HttpStatus.OK
        );
        assertEquals(expected.toString().contains("data"),customerService.createCustomer(request).toString().contains("data"));

    }



    @AfterEach
    public void removeData()
    {

        accountRepository.deleteAll();
        customerRepository.deleteAll();
        currencyRepository.deleteAll();



    }
}