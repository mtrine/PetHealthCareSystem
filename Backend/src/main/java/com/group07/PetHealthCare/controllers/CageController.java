package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.CageRequest;
import com.group07.PetHealthCare.dto.response.CageResponse;
import com.group07.PetHealthCare.pojo.Cage;
import com.group07.PetHealthCare.service.CagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cages")
public class CageController {
    @Autowired
    private CagesService cagesService;

    @GetMapping
    public ApiResponse<List<CageResponse>> getAllCages() {
        ApiResponse<List<CageResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cagesService.getAllCages());
        return  apiResponse;
    }

    @PostMapping
    public ApiResponse<CageResponse> createCage(@RequestBody CageRequest request) {
        ApiResponse<CageResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cagesService.addCage(request));
        return apiResponse;
    }

    @GetMapping("/{id}")
    public ApiResponse<CageResponse> getCageById(@PathVariable Integer id) {
        ApiResponse<CageResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(cagesService.getCageById(id));
        return apiResponse;
    }
}
