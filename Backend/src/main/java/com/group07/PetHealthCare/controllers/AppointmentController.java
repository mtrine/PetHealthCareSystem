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

    @PostMapping("/assignVeterinarian/{appointmentId}")
    public ApiResponse<Appointment> assignVeterinarianToAppointment(
            @PathVariable("appointmentId") String appointmentId,
            @RequestBody String requestBody) {
        // Parse the veterinarianId from the JSON request body
        ObjectMapper objectMapper = new ObjectMapper();
        String veterinarianId;
        try {
            JsonNode jsonNode = objectMapper.readTree(requestBody);
            veterinarianId = jsonNode.get("veterinarianId").asText();
        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON format", e);
        }

        System.out.println("veterinarianId: " + veterinarianId);
        ApiResponse<Appointment> apiResponse = new ApiResponse<>();
        Appointment appointment = appointmentService.assignVeterinarianToAppointment(appointmentId, veterinarianId);
        apiResponse.setResult(appointment);
        return apiResponse;
    }
    @GetMapping
    public ApiResponse<List<Appointment>> getAllAppointments() {
        ApiResponse<List<Appointment>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.getAllAppointments());
        return apiResponse;
    }
}
