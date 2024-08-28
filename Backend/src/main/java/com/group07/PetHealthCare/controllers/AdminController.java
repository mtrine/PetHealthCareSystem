package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.response.AdminResponse;
import com.group07.PetHealthCare.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/admins")
public class AdminController {
    @Autowired
    AdminService adminService;

    @GetMapping
    public ApiResponse<List<AdminResponse>> getAllAdmin() {
        ApiResponse<List<AdminResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(adminService.getAllAdmins());
        return apiResponse;
    }

    @GetMapping("/{userId}")
    public ApiResponse<AdminResponse> getAdmin(@PathVariable String userId) {
        ApiResponse<AdminResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(adminService.getAdminById(userId));
        return apiResponse;
    }
}
