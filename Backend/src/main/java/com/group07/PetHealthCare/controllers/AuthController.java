package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.*;
import com.group07.PetHealthCare.dto.response.AuthResponse;
import com.group07.PetHealthCare.dto.response.IntrospectRespone;
import com.group07.PetHealthCare.dto.response.UserResponse;
import com.group07.PetHealthCare.service.AuthService;

import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Slf4j
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
    public ApiResponse<AuthResponse>   login(@RequestBody @Valid UserRequest user) {


        ApiResponse<AuthResponse> apiResponse= new ApiResponse<>();
        apiResponse.setResult(authService.login(user));
        return apiResponse;
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectRespone> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        ApiResponse<IntrospectRespone> apiResponse= new ApiResponse<>();
        apiResponse.setResult(authService.introspect(request));
        return apiResponse;
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        ApiResponse<Void> apiResponse= new ApiResponse<>();
        authService.logout(request);
        return apiResponse;
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refreshToken(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        ApiResponse<AuthResponse> apiResponse= new ApiResponse<>();
        apiResponse.setResult(authService.refreshToken(request));
        return apiResponse;
    }

}
