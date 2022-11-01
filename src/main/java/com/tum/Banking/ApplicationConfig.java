package com.tum.Banking;

import com.tum.Banking.service.Account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Autowired
    AccountService accountService;

    @Bean
    CommandLineRunner initDatabase()
    {

        return args -> {
            accountService.populateCurrencyTable();
        };
    }
}
