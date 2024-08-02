package com.group07.PetHealthCare.controller;

import com.group07.PetHealthCare.dto.request.VaccineCreationRequest;
import com.group07.PetHealthCare.dto.request.VaccineUpdateRequest;
import com.group07.PetHealthCare.entity.Vaccine;
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
    Vaccine createVaccine(@RequestBody VaccineCreationRequest request) {
        return vaccineService.createRequest(request);
    }


    @GetMapping
    List <Vaccine> getVaccines() {
        return vaccineService.getVaccines();
    }
    @GetMapping("/{Vaccineid}")
    Vaccine getVaccine(@PathVariable("Vaccineid") String Vaccineid) {
        return vaccineService.getVaccine(Vaccineid);
    }


    @PutMapping("/{Vaccineid}")
    Vaccine updateVaccine(@PathVariable String vaccineid ,@RequestBody VaccineUpdateRequest request) {
        return vaccineService.updateVaccine(vaccineid,request);
    }


    @DeleteMapping("/{vaccineid}")
    String deleteVaccine(@PathVariable String vaccineid) {
        vaccineService.deleteVaccine(vaccineid);
        return "Vaccine has been deleted";
    }

}
