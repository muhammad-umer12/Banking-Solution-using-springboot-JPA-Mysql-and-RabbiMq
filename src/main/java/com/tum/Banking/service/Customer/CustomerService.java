package com.tum.Banking.service.Customer;

import com.google.gson.Gson;
import com.tum.Banking.dto.CustomerRequestDTO;
import com.tum.Banking.model.Customer.Customer;
import com.tum.Banking.repository.Customer.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;


    public ResponseEntity<?> createCustomer(CustomerRequestDTO customerRequest) {

        try {
            Customer customer = new Customer();
            customer.setContact(customerRequest.getContact());
            customer.setEmail(customerRequest.getEmail());
            customer.setName(customerRequest.getName());

            customerRepository.save(customer);
            Gson gson = new Gson();
            String result = gson.toJson(customer);
            // return new ResponseEntity<String>(result, HttpStatus.OK);

            return new ResponseEntity<String>(
                    new JSONObject("{'success':  true,'data':" + result + "  }").toString(), HttpStatus.OK
            );



        }
        catch(Exception e)
        {
            return new ResponseEntity<String>(
                    new JSONObject("{'success':  false,'message':" + e.getMessage() + "  }").toString(), HttpStatus.BAD_REQUEST
            );
        }
    }

}
