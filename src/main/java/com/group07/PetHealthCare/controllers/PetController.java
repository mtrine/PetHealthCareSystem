package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.PetCreationRequest;
import com.group07.PetHealthCare.dto.request.PetUpdateRequest;
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
    public ApiResponse<Pet> createPet(@RequestBody PetCreationRequest request) {
        ApiResponse<Pet> apiResponse = new ApiResponse<>();
        apiResponse.setResult(petService.addPet(request));
        return apiResponse;
    }

    @GetMapping("/{customerId}")
    public ApiResponse<Set<Pet>> getPetsByCustomerId(@PathVariable String customerId) {
        ApiResponse<Set<Pet>> apiResponse = new ApiResponse<>();
        try {
            Set<Pet> pets = petService.getPetsByCustomerId(customerId);
                apiResponse.setResult(pets);
                apiResponse.setMessage("Pets retrieved successfully");
            return apiResponse;

        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApiResponse<Pet> updatePet(@PathVariable String id, @RequestBody PetUpdateRequest request) {
        ApiResponse<Pet> apiResponse = new ApiResponse<>();
        try {
            Pet updatedPet = petService.updatePet(id, request);
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
}
