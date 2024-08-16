package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.response.StaffResponse;
import com.group07.PetHealthCare.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/staffs")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @GetMapping
    public ApiResponse<List<StaffResponse>> getAllStaffs() {
        ApiResponse<List<StaffResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(staffService.getAllStaff());
        return apiResponse;
    }
    @GetMapping("/{staffId}")
    public ApiResponse<StaffResponse> getStaffById(@PathVariable("staffId") String staffId) {
        ApiResponse<StaffResponse>  apiResponse = new ApiResponse<>();
        apiResponse.setResult(staffService.getStaffById(staffId));
        return apiResponse;
    }


}
