package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.dto.respone.AuthRespone;
import com.group07.PetHealthCare.dto.respone.UserRespone;
import com.group07.PetHealthCare.pojo.User;
import com.group07.PetHealthCare.service.AuthService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Authenticator;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ApiResponse<UserRespone> registerUser(@RequestBody @Valid UserRequest user) {
        ApiResponse<UserRespone>  apiResponse= new ApiResponse<>();
        UserRespone registeredUser = authService.register(user);
        apiResponse.setResult(registeredUser);
        return  apiResponse;
    }

    @PostMapping("/login")
    public ApiResponse<AuthRespone>   login(@RequestBody @Valid UserRequest user) throws JOSEException {
        ApiResponse<AuthRespone> apiResponse= new ApiResponse<>();
        apiResponse.setResult(authService.login(user));
        return apiResponse;
    }
}
