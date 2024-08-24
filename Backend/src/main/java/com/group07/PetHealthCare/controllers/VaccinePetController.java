package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.VaccinePetRequest;
import com.group07.PetHealthCare.dto.response.VaccinePetResponse;
import com.group07.PetHealthCare.pojo.VaccinePet;
import com.group07.PetHealthCare.service.VaccinePetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/vaccines-pet")
public class VaccinePetController {
    @Autowired
    private VaccinePetService vaccinePetService;

    @GetMapping("/{petId}/pets")
    public ApiResponse<List<VaccinePetResponse>> getVaccinePetByPetId(@PathVariable("petId") String id) {
        ApiResponse<List<VaccinePetResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(vaccinePetService.getVaccinePetByPetId(id));
        return apiResponse;
    }

    @PostMapping
    public ApiResponse<VaccinePetResponse> addVaccineToPet(@RequestBody VaccinePetRequest request){
        ApiResponse<VaccinePetResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(vaccinePetService.addVaccineToPet(request));
        return apiResponse;
    }
}
