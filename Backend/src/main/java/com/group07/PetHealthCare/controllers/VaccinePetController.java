package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.VaccinePetRequest;
import com.group07.PetHealthCare.pojo.VaccinePet;
import com.group07.PetHealthCare.service.VaccinePetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/pets/vaccines")
public class VaccinePetController {
    @Autowired
    private VaccinePetService vaccinePetService;

    @GetMapping("/{petId}")
    public ApiResponse<List<VaccinePet>> getVaccinePetByPetId(@PathVariable("petId") String id) {
        ApiResponse<List<VaccinePet>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(vaccinePetService.getVaccinePetByPetId(id));
        return apiResponse;
    }

    @PostMapping
    public ApiResponse<VaccinePet> addVaccineToPet(@RequestBody VaccinePetRequest request){
        ApiResponse<VaccinePet> apiResponse = new ApiResponse<>();
        apiResponse.setResult(vaccinePetService.addVaccineToPet(request));
        return apiResponse;
    }
}
