package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.dto.response.AuthResponse;
import com.group07.PetHealthCare.dto.response.UserResponse;
import com.group07.PetHealthCare.service.AuthService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> registerUser(@RequestBody @Valid UserRequest user) {
        ApiResponse<UserResponse>  apiResponse= new ApiResponse<>();
        UserResponse registeredUser = authService.register(user);
        apiResponse.setResult(registeredUser);
        return  apiResponse;
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse>   login(@RequestBody @Valid UserRequest user) throws JOSEException {
        ApiResponse<AuthResponse> apiResponse= new ApiResponse<>();
        apiResponse.setResult(authService.login(user));
        return apiResponse;
    }
}
