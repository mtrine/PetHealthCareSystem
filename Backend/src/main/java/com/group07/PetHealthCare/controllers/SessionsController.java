package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.SessionsRequest;
import com.group07.PetHealthCare.dto.response.SessionResponse;
import com.group07.PetHealthCare.pojo.Session;
import com.group07.PetHealthCare.service.SessionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/sessions")
public class SessionsController {
    @Autowired
    private SessionsService sessionsService;
    @PostMapping
    public ApiResponse<SessionResponse> addSession(@RequestBody SessionsRequest session) {
        ApiResponse<SessionResponse> apiResponse=new ApiResponse<>();
        apiResponse.setResult(sessionsService.createSession(session));
        return apiResponse;
    }
    @GetMapping
    public ApiResponse<List<SessionResponse>> getAllSession(){
        ApiResponse<List<SessionResponse>> apiResponse=new ApiResponse<>();
        apiResponse.setResult(sessionsService.getAllSession());
        return apiResponse;
    }
}
