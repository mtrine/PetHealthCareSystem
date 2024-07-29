package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.AppointmentRequest;
import com.group07.PetHealthCare.pojo.Appointment;
import com.group07.PetHealthCare.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @PostMapping
    public ApiResponse<Appointment> addAppointment(@RequestBody AppointmentRequest request) {
        ApiResponse<Appointment> apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.addAppointment(request));
        return apiResponse;
    }
    @GetMapping
    public ApiResponse<List<Appointment>> getAllAppointments() {
        ApiResponse<List<Appointment>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.getAllAppointments());
        return apiResponse;
    }
}
