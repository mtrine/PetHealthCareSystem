package com.group07.PetHealthCare.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @PostMapping("/addBySession")
    public ApiResponse<Appointment> addAppointmentBySession(@RequestBody AppointmentRequest request) {
        ApiResponse<Appointment> apiResponse = new ApiResponse<>();
        Appointment appointment = appointmentService.addAppointmentBySession(request);
        apiResponse.setResult(appointment);
        return apiResponse;
    }

    @PostMapping("/addByVeterinarian")
    public ApiResponse<Appointment> addAppointmentByVeterinarian(@RequestBody AppointmentRequest request) {
        ApiResponse<Appointment> apiResponse = new ApiResponse<>();
            Appointment appointment = appointmentService.addAppointmentByVeterinarian(request);
           apiResponse.setResult(appointment);
            return apiResponse;
    }

    @GetMapping("/{veterinarianId}")
    public ApiResponse<List<Appointment>> getAppointmentByVeterinarianId(@PathVariable("veterinarianId") String veterinarianId) {
        ApiResponse<List<Appointment>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.getAppointmentByVeterinarianId(veterinarianId));
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<Appointment>> getAllAppointments() {
        ApiResponse<List<Appointment>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.getAllAppointments());
        return apiResponse;
    }
    @PatchMapping("/{appointmentId}")
    public ApiResponse<Appointment> changeInforAppointment(@PathVariable("appointmentId") String appointmentId,@RequestBody  AppointmentRequest request){
        ApiResponse<Appointment> apiResponse = new ApiResponse<>();
        System.out.println("Service Id hello: " + request.getServiceId());
        apiResponse.setResult(appointmentService.changeInforAppointment(appointmentId,request));
        return apiResponse;
    }
}
