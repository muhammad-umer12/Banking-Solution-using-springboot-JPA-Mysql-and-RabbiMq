package com.tum.Banking.repository.Account;

import com.tum.Banking.model.Account.Currency;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CurrencyRepositoryTest {

    @Autowired
    CurrencyRepository currencyRepository;
    @Test
    void findCurrencies() {

        List<Currency> currencyList = new ArrayList<>();
        currencyList.add(new Currency("EUR"));
        currencyList.add(new Currency("USD"));
        List<Currency> currencyResult = currencyRepository.saveAll(currencyList);
        List<String> names = new ArrayList<>();
        names.add("EUR");
        names.add("USD");
        List<Currency> actualResult = currencyRepository.findCurrencies(names);
        assertEquals(actualResult,currencyResult);
    }

    @Test
    void findCurrencyByName() {

        Currency currencyResult = currencyRepository.save(new Currency("USD"));
        Currency actualResult = currencyRepository.findCurrencyByName("USD");
        assertEquals(actualResult,currencyResult);
    }


    @AfterEach
    public void removeData()
    {
        currencyRepository.deleteAll();
    }
}