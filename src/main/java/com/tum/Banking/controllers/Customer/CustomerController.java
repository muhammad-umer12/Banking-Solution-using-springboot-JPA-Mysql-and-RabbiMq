package com.tum.Banking.controllers.Customer;

import com.tum.Banking.dto.CustomerRequestDTO;
import com.tum.Banking.service.Customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customer")

public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {

        return customerService.createCustomer(customerRequestDTO);
    }
}
