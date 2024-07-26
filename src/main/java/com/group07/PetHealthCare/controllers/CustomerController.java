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

    @GetMapping
    public ApiResponse<List<Customer>> getAllCustomers() {
        ApiResponse<List<Customer>> apiResponse=new ApiResponse<>();
        apiResponse.setResult(customerService.getAllCustomers());
        return apiResponse;
    }


    @GetMapping("/{userID}")
    public ApiResponse<Optional<Customer>> getCustomerById(@PathVariable("userID") String userID) {
        ApiResponse<Optional<Customer>> apiResponse=new ApiResponse<>();
        apiResponse.setResult(customerService.getCustomerById(userID));
        return apiResponse;
    }

    @DeleteMapping("/{userID}")
    public ApiResponse<String> deleteCustomer (@PathVariable("userID") String userID){
        customerService.deleteCustomersById(userID);
        ApiResponse<String> apiResponse=new ApiResponse<>();
        apiResponse.setResult("Deleted successfully");
        return apiResponse;
    }
}
