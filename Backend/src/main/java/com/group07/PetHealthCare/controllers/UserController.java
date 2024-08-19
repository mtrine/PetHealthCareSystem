package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.dto.response.UserResponse;
import com.group07.PetHealthCare.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private UserService userService;


    @PatchMapping("/{userId}")
    public ApiResponse<UserResponse>  updateUser(@PathVariable("userId") String userId, @RequestBody @Valid UserRequest request) {
       ApiResponse<UserResponse> apiResponse= new ApiResponse<>();
       apiResponse.setResult(userService.updateInforUser(userId,request));
       return apiResponse;
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<String>  deleteUser(@PathVariable("userId") String userId) {
        ApiResponse<String> apiResponse= new ApiResponse<>();
        userService.deleteUser(userId);
        apiResponse.setResult("Deleted");
        return apiResponse;
    }
}
