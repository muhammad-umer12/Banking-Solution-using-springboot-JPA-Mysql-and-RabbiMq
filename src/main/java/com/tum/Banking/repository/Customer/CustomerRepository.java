package com.tum.Banking.repository.Customer;

import com.tum.Banking.model.Customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}