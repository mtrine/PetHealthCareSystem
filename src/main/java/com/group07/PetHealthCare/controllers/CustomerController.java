package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.UserCreationRequest;
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
    public ApiResponse<Customer>  createCustomer(@RequestBody @Valid UserCreationRequest customer) {
        ApiResponse<Customer> apiResponse = new ApiResponse<>();

        apiResponse.setResult(customerService.createCustomer(customer));
        return apiResponse;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/login")
    public ApiResponse<Optional<Customer>> loginCustomer(@RequestBody UserCreationRequest customer){
        ApiResponse<Optional<Customer>> apiResponse= new ApiResponse<>();
        apiResponse.setResult(customerService.loginCustomer(customer));
        return apiResponse;
    }
    @GetMapping("/{userID}")
    public Optional<Customer> getCustomerById(@PathVariable("userID") String userID) {
        return customerService.getCustomerById(userID);
    }
}
