package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.HospitalizationRequest;
import com.group07.PetHealthCare.dto.response.HospitalizationResponse;
import com.group07.PetHealthCare.pojo.Hospitalization;
import com.group07.PetHealthCare.service.HospitalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/hospitalizations")
public class HospitalizationController {

    @Autowired
    private HospitalizationService hospitalizationService;

    @PostMapping
    public ApiResponse<HospitalizationResponse> createHospitalization(@RequestBody HospitalizationRequest request) {
        ApiResponse<HospitalizationResponse>  apiResponse = new ApiResponse<>();
        apiResponse.setResult(hospitalizationService.createHospitalization(request));
        return apiResponse;
    }

    @PatchMapping("/{hospitalizationId}")
    public ApiResponse<HospitalizationResponse> updateHospitalization(@PathVariable("hospitalizationId") String hospitalizationId,
                                                              @RequestBody HospitalizationRequest request) {
        ApiResponse<HospitalizationResponse>  apiResponse = new ApiResponse<>();
        apiResponse.setResult(hospitalizationService.updateHospitalization(hospitalizationId, request));
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<HospitalizationResponse>> getAllHospitalizations() {
        ApiResponse<List<HospitalizationResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(hospitalizationService.getAllHospitalization());
        return apiResponse;
    }

    @DeleteMapping("/{hospitalizationId}")
    public ApiResponse<String> deleteHospitalization(@PathVariable("hospitalizationId") String hospitalizationId) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        hospitalizationService.deleteHospitalization(hospitalizationId);
        apiResponse.setResult("Deleted Successfully");
        return apiResponse;
    }

    @GetMapping("/{hospitalizationId}")
    public ApiResponse<HospitalizationResponse> getHospitalization(@PathVariable("hospitalizationId") String hospitalizationId) {
        ApiResponse<HospitalizationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(hospitalizationService.getHospitalizationById(hospitalizationId));
        return apiResponse;
    }

    @GetMapping(params = "petId")
    public ApiResponse<List<HospitalizationResponse>> getHospitalizationByPet(@RequestParam("petId") String petId) {
        ApiResponse<List<HospitalizationResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(hospitalizationService.getHospitalizationByPetId(petId));
        return apiResponse;
    }
}