package com.group07.PetHealthCare.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.AppointmentRequest;
import com.group07.PetHealthCare.dto.response.AppointmentResponse;
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
    public ApiResponse<AppointmentResponse> addAppointmentBySession(@RequestBody AppointmentRequest request) {
        ApiResponse<AppointmentResponse>apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.addAppointmentBySession(request));
        return apiResponse;
    }

    @PostMapping("/addByVeterinarian")
    public ApiResponse<AppointmentResponse> addAppointmentByVeterinarian(@RequestBody AppointmentRequest request) {
        ApiResponse<AppointmentResponse> apiResponse = new ApiResponse<>();
            AppointmentResponse appointment = appointmentService.addAppointmentByVeterinarian(request);
           apiResponse.setResult(appointment);
            return apiResponse;
    }

    @GetMapping("/{veterinarianId}/veterinarians")
    public ApiResponse<List<AppointmentResponse>> getAppointmentByVeterinarianId(@PathVariable("veterinarianId") String veterinarianId) {
        ApiResponse<List<AppointmentResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.getAppointmentByVeterinarianId(veterinarianId));
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<AppointmentResponse>> getAllAppointments() {
        ApiResponse<List<AppointmentResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.getAllAppointments());
        return apiResponse;
    }
    @PatchMapping("/{appointmentId}")
    public ApiResponse<AppointmentResponse> changeInforAppointment(@PathVariable("appointmentId") String appointmentId,@RequestBody  AppointmentRequest request){
        ApiResponse<AppointmentResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.changeInforAppointment(appointmentId,request));
        return apiResponse;
    }

    @GetMapping("/{custometId}/customer")
    public ApiResponse<List<AppointmentResponse>> getAppointmentByCustometId(@PathVariable("custometId") String custometId) {
        ApiResponse<List<AppointmentResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.getAppointmentByCustomerId(custometId));
        return apiResponse;
    }

    @GetMapping("/get-my-appointment-for-customer")
    public ApiResponse<List<AppointmentResponse>> getMyAppointmentForCustomer() {
        ApiResponse<List<AppointmentResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.getMyAppointmentForCustomer());
        return apiResponse;
    }

    @GetMapping("/{petId}/pets")
    public  ApiResponse<List<AppointmentResponse>> getAppointmentByPetId(@PathVariable("petId") String petId) {
        ApiResponse<List<AppointmentResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(appointmentService.getAppointmentByPetId(petId));
        return apiResponse;
    }
}
