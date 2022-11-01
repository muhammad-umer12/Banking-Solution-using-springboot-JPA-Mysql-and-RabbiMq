package com.tum.Banking.repository.Account;

import com.tum.Banking.model.Account.CustomerAccount;
import com.tum.Banking.model.Customer.Customer;
import com.tum.Banking.repository.Customer.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)

class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void findById() {

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


        CustomerAccount actualResut = accountRepository.findById(id);

        assertEquals(account.getId(),actualResut.getId());

        System.out.println("Test Account find by id passed");

    }

    @AfterEach
    public void removeData()
    {
        customerRepository.deleteAll();
        accountRepository.deleteAll();
    }

}