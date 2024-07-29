package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.UserCreationRequest;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.pojo.User;
import com.group07.PetHealthCare.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<User>  registerUser(@RequestBody @Valid UserCreationRequest user) {
            ApiResponse<User> apiResponse= new ApiResponse<>();
            User registeredUser = userService.register(user);
            apiResponse.setResult(registeredUser);
            return  apiResponse;
    }

    @PostMapping("/login")
    public ApiResponse<User>  login(@RequestBody @Valid UserCreationRequest user) {
        ApiResponse<User> apiResponse= new ApiResponse<>();
        apiResponse.setResult(userService.login(user));
        return apiResponse;
    }
}
