package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.CustomerCreationRequest;
import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public Customer createCustomer(@RequestBody @Valid CustomerCreationRequest customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/login")
    public Optional<Customer> loginCustomer(@RequestBody CustomerCreationRequest customer){
        return customerService.loginCustomer(customer);
    }
    @GetMapping("/{userID}")
    public Optional<Customer> getCustomerById(@PathVariable("userID") String userID) {
        return customerService.getCustomerById(userID);
    }
}
