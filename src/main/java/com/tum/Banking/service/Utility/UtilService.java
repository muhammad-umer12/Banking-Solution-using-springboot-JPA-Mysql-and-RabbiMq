package com.tum.Banking.service.Utility;


import com.tum.Banking.model.Account.Currency;
import com.tum.Banking.repository.Account.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j

public class UtilService {

    @Autowired
    CurrencyRepository currencyRepository;

    public boolean validateCurrency(List<String> currencies)
    {
        List<Currency> existingCurrencies = currencyRepository.findAll();
        HashSet<String> map = new HashSet<>();

        for(Currency currency: existingCurrencies)
        {
            map.add(currency.getName());
        }

        for(String currency: currencies)
        {
            if(!map.contains(currency))
            {
                return false;
            }
        }

        return true;

    }


}