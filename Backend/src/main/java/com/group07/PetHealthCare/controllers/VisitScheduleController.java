package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.VisitScheduleRequest;
import com.group07.PetHealthCare.pojo.VisitSchedule;
import com.group07.PetHealthCare.service.VisitScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/visitschedules")
public class VisitScheduleController {

    @Autowired
    private VisitScheduleService visitScheduleService;

   @PostMapping
    public ApiResponse<VisitSchedule> createVisitSchedule(@RequestBody VisitScheduleRequest visitScheduleRequest) {
        ApiResponse<VisitSchedule> apiResponse=new ApiResponse<>();
        VisitSchedule visitSchedule=visitScheduleService.createVisitSchedule(visitScheduleRequest);
        apiResponse.setResult(visitSchedule);
        return apiResponse;
    }
}
