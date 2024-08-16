package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.response.CustomerResponse;
import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ApiResponse<List<CustomerResponse>> getAllCustomers() {
        ApiResponse<List<CustomerResponse>> apiResponse=new ApiResponse<>();
        apiResponse.setResult(customerService.getAllCustomers());
        return apiResponse;
    }


    @GetMapping("/{userID}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable("userID") String userID) {
        ApiResponse<CustomerResponse> apiResponse=new ApiResponse<>();
        apiResponse.setResult(customerService.getCustomerById(userID));
        return apiResponse;
    }


}
