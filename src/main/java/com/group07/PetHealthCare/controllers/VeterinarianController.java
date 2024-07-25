package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.UserCreationRequest;
import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.pojo.Veterinarian;
import com.group07.PetHealthCare.service.CustomerService;
import com.group07.PetHealthCare.service.VeterinarianService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/veterinarians")
public class VeterinarianController {

    @Autowired
    private VeterinarianService veterinarianService;

    @GetMapping
    public List<Veterinarian> getAllCustomers() {
        return veterinarianService.getAllVeterinarian();
    }

    //    @PostMapping("/login")
//    public ApiResponse<Optional<Customer>> loginCustomer(@RequestBody UserCreationRequest customer){
//        ApiResponse<Optional<Customer>> apiResponse= new ApiResponse<>();
//        apiResponse.setResult(customerService.loginCustomer(customer));
//        return apiResponse;
//    }
    @GetMapping("/{veterinarianID}")
    public Optional<Veterinarian> getVeterinarianById(@PathVariable("veterinarianID") String veterinarianID) {
        return veterinarianService.getVeterinarianById(veterinarianID);
    }

    @DeleteMapping("/{veterinarianID}")
    public void deleteVeterinarian (@PathVariable("veterinarianID") String veterinarianID){
        veterinarianService.deleteVeterinarianById(veterinarianID);
    }
}

