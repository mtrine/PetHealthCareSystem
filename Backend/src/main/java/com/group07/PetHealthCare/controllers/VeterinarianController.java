package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.response.VeterinarianResponse;
import com.group07.PetHealthCare.pojo.Veterinarian;
import com.group07.PetHealthCare.service.VeterinarianService;
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
    public ApiResponse<List<VeterinarianResponse>> getAllCustomers() {
        ApiResponse<List<VeterinarianResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(veterinarianService.getAllVeterinarian());
        return apiResponse;
    }

    @GetMapping("/{veterinarianID}")
    public ApiResponse<VeterinarianResponse> getVeterinarianById(@PathVariable("veterinarianID") String veterinarianID) {
        ApiResponse<VeterinarianResponse>  apiResponse = new ApiResponse<>();
            apiResponse.setResult(veterinarianService.getVeterinarianById(veterinarianID));
            return apiResponse;
    }

    @DeleteMapping("/{veterinarianID}")
    public ApiResponse<String> deleteVeterinarian (@PathVariable("veterinarianID") String veterinarianID){
        veterinarianService.deleteVeterinarianById(veterinarianID);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResult("Delete success");
        return apiResponse;
    }

}

