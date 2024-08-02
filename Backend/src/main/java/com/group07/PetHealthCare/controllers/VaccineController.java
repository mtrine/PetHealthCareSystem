package com.group07.PetHealthCare.controller;


import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.VaccineRequest;
import com.group07.PetHealthCare.pojo.Vaccine;
import com.group07.PetHealthCare.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/vaccines")
public class VaccineController {
    @Autowired
    private VaccineService vaccineService;


    @PostMapping
    ApiResponse<Vaccine> createVaccine(@RequestBody VaccineRequest request) {
        ApiResponse<Vaccine> apiResponse = new ApiResponse<>();
        apiResponse.setResult(vaccineService.createVaccine(request));
        return apiResponse ;
    }


    @GetMapping
    ApiResponse<List <Vaccine>> getVaccines() {
        ApiResponse<List <Vaccine>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(vaccineService.getVaccines());
        return apiResponse ;
    }
    @GetMapping("/{Vaccineid}")
    ApiResponse<Vaccine> getVaccine(@PathVariable("Vaccineid") String Vaccineid) {
        ApiResponse<Vaccine> apiResponse = new ApiResponse<>();
        apiResponse.setResult(vaccineService.getVaccine(Vaccineid));
        return apiResponse ;
    }


    @PutMapping("/{Vaccineid}")
    ApiResponse<Vaccine> updateVaccine(@PathVariable String vaccineid ,@RequestBody VaccineRequest request) {
        ApiResponse<Vaccine> apiResponse= new ApiResponse<>();
        apiResponse.setResult(vaccineService.updateVaccine(vaccineid,request));
        return apiResponse;
    }


    @DeleteMapping("/{vaccineid}")
    ApiResponse<String> deleteVaccine(@PathVariable String vaccineid) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        vaccineService.deleteVaccine(vaccineid);
        apiResponse.setResult("Vaccine deleted successfully");
        return apiResponse;
    }

}