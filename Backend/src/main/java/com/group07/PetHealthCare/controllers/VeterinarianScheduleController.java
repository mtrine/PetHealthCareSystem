package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.VeterinarianScheduleRequest;
import com.group07.PetHealthCare.pojo.Veterinarianschedule;
import com.group07.PetHealthCare.service.VeterinarianScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/veterinarianSchedules")
public class VeterinarianScheduleController {
    @Autowired
    private VeterinarianScheduleService veterinarianScheduleService;

    @PostMapping
    public ApiResponse<Veterinarianschedule> addVeterinarianSchedule(@RequestBody VeterinarianScheduleRequest request) {
        ApiResponse<Veterinarianschedule> apiResponse= new ApiResponse<>();
        apiResponse.setResult(veterinarianScheduleService.addSchedule(request));
        return apiResponse;
    }
}
