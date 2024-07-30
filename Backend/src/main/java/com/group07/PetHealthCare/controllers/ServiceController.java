package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.ServiceRequest;
import com.group07.PetHealthCare.pojo.Services;
import com.group07.PetHealthCare.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/services")
public class ServiceController {
    @Autowired
    private ServicesService servicesService;

    @PostMapping
    public ApiResponse<Services> createService(@RequestBody ServiceRequest request) {
        ApiResponse<Services> apiResponse = new ApiResponse<>();
        apiResponse.setResult(servicesService.createService(request));
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<Services>> getServices() {
        ApiResponse<List<Services>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(servicesService.getAllServices());
        return apiResponse;
    }
}