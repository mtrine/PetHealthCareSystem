package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.VisitScheduleRequest;
import com.group07.PetHealthCare.dto.response.VisitScheduleResponse;
import com.group07.PetHealthCare.pojo.VisitSchedule;
import com.group07.PetHealthCare.service.VisitScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/visitschedules")
public class VisitScheduleController {

    @Autowired
    private VisitScheduleService visitScheduleService;

   @PostMapping
    public ApiResponse<VisitScheduleResponse> createVisitSchedule(@RequestBody VisitScheduleRequest visitScheduleRequest) {
       ApiResponse<VisitScheduleResponse> apiResponse=new ApiResponse<>();
        apiResponse.setResult(visitScheduleService.createVisitSchedule(visitScheduleRequest));
        return apiResponse;
    }

    @GetMapping("/my-visit-schedule")
    public ApiResponse<List<VisitScheduleResponse>> getMyVisitSchedule() {
       ApiResponse<List<VisitScheduleResponse>> apiResponse=new ApiResponse<>();
       apiResponse.setResult(visitScheduleService.getMyVisitSchedules());
       return apiResponse;
    }
}
