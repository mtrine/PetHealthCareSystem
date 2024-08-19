package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.PetCreationRequest;
import com.group07.PetHealthCare.dto.request.PetUpdateRequest;
import com.group07.PetHealthCare.dto.response.PetResponse;
import com.group07.PetHealthCare.pojo.Pet;
import com.group07.PetHealthCare.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/v1/pets")
public class PetController {
    @Autowired
    private PetService petService;

    @PostMapping
    public ApiResponse<PetResponse> createPet(@RequestBody PetCreationRequest request) {
        ApiResponse<PetResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(petService.addPet(request));
        return apiResponse;
    }

    @GetMapping("/customers/{customerId}")
    public ApiResponse<Set<PetResponse>> getPetsByCustomerId(@PathVariable String customerId) {
        ApiResponse<Set<PetResponse>> apiResponse = new ApiResponse<>();
        try {
            Set<PetResponse> pets = petService.getPetsByCustomerId(customerId);
                apiResponse.setResult(pets);
                apiResponse.setMessage("Pets retrieved successfully");
            return apiResponse;

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<PetResponse> updatePet(@PathVariable String id, @RequestBody PetUpdateRequest request) {
        ApiResponse<PetResponse>  apiResponse = new ApiResponse<>();
        try {
            PetResponse updatedPet = petService.updatePet(id, request);
            apiResponse.setResult(updatedPet);
            apiResponse.setMessage("Pet updated successfully");
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePet(@PathVariable String id) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        try {
            petService.deletePet(id);
            apiResponse.setMessage("Pet deleted successfully");
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
        return apiResponse;
    }
    @PostMapping("/add-my-pet")
    public ApiResponse<PetResponse> addMyPet(@RequestBody PetCreationRequest request) {
        ApiResponse<PetResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(petService.addMyPet(request));
        return apiResponse;
    }

    @GetMapping("/get-my-pet-list")
    public ApiResponse<Set<PetResponse>> getMyPetList() {
        ApiResponse<Set<PetResponse>> apiResponse = new ApiResponse<>();
        Set<PetResponse> pets = petService.getMyPetList();
        apiResponse.setResult(pets);
        return apiResponse;
    }
}
