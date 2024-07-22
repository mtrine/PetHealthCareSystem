package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.CustomerCreationRequest;
import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerCreationRequest customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
    }
}
