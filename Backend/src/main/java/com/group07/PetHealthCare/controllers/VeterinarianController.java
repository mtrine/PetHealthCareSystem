package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.response.SessionResponse;
import com.group07.PetHealthCare.dto.response.VeterinarianResponse;
import com.group07.PetHealthCare.pojo.Veterinarian;
import com.group07.PetHealthCare.service.VeterinarianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/veterinarians")
public class VeterinarianController {

    @Autowired
    private VeterinarianService veterinarianService;

    @GetMapping
    public ApiResponse<List<VeterinarianResponse>> getAllCustomers() {
        ApiResponse<List<VeterinarianResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(veterinarianService.getAllVeterinarian());
        return apiResponse;
    }

    @GetMapping("/{veterinarianID}")
    public ApiResponse<VeterinarianResponse> getVeterinarianById(@PathVariable("veterinarianID") String veterinarianID) {
        ApiResponse<VeterinarianResponse>  apiResponse = new ApiResponse<>();
            apiResponse.setResult(veterinarianService.getVeterinarianById(veterinarianID));
            return apiResponse;
    }

    @GetMapping("/{veterinarianID}/available-sessions")
    public ApiResponse<List<SessionResponse>> getAvailableSessions(
            @PathVariable("veterinarianID") String veterinarianID,
            @RequestParam("date") LocalDate appointmentDate) {

        ApiResponse<List<SessionResponse>> apiResponse = new ApiResponse<>();
        List<SessionResponse> availableSessions = veterinarianService.getAvailableSessionsForVeterinarian(veterinarianID, appointmentDate);
        apiResponse.setResult(availableSessions);
        return apiResponse;
    }

    @GetMapping("/{sessionId}/get-avai")
    public ApiResponse<List<VeterinarianResponse>> getAvailableVeterinariansForSessionAndDate() {
        ApiResponse<List<VeterinarianResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(veterinarianService.getAllVeterinarian());
        return apiResponse;
    }
}

