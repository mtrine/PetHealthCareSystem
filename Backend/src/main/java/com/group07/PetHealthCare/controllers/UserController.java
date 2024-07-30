package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.pojo.User;
import com.group07.PetHealthCare.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<User>  registerUser(@RequestBody @Valid UserRequest user) {
            ApiResponse<User> apiResponse= new ApiResponse<>();
            User registeredUser = userService.register(user);
            apiResponse.setResult(registeredUser);
            return  apiResponse;
    }

    @PostMapping("/login")
    public ApiResponse<User>  login(@RequestBody @Valid UserRequest user) {
        ApiResponse<User> apiResponse= new ApiResponse<>();
        apiResponse.setResult(userService.login(user));
        return apiResponse;
    }

    @PutMapping("/{userId}")
    public ApiResponse<User>  updateUser(@PathVariable("userd") String userId, @RequestBody @Valid UserRequest request) {
       ApiResponse<User> apiResponse= new ApiResponse<>();
       apiResponse.setResult(userService.updateInforUser(userId,request));
       return apiResponse;
    }
}
