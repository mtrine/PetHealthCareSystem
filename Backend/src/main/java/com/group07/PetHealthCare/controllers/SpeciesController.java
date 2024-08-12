package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.SpeciesRequest;
import com.group07.PetHealthCare.dto.response.SpeciesResponse;
import com.group07.PetHealthCare.pojo.Species;
import com.group07.PetHealthCare.service.SpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/species")
public class SpeciesController {
    @Autowired
    private SpeciesService speciesService;

    @PostMapping
    public ApiResponse<SpeciesResponse> getSpeciesService(@RequestBody SpeciesRequest request) {
        ApiResponse<SpeciesResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(speciesService.addSpecies(request));
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<SpeciesResponse>> getSpeciesService() {
        ApiResponse<List<SpeciesResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(speciesService.getAllSpecies());
        return apiResponse;
    }
}
