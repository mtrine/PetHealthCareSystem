package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.PetCreationRequest;
import com.group07.PetHealthCare.pojo.Pet;
import com.group07.PetHealthCare.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
