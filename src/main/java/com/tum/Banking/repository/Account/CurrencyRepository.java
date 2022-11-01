package com.tum.Banking.repository.Account;

import com.tum.Banking.model.Account.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CurrencyRepository  extends JpaRepository<Currency, Integer> {

    @Query(value = "SELECT c.* FROM currency c WHERE c.name IN (:currencies)", nativeQuery = true)
    List<Currency> findCurrencies(@Param("currencies") List<String> currency );

    @Query(value = "SELECT c.* FROM currency c WHERE c.name =:name  limit 1", nativeQuery = true)
    Currency findCurrencyByName(@Param("name") String name);


}