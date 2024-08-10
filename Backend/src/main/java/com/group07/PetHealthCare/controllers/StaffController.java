package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.respone.StaffRespone;
import com.group07.PetHealthCare.pojo.Staff;
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
    public ApiResponse<List<StaffRespone>> getAllStaffs() {
        ApiResponse<List<StaffRespone>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(staffService.getAllStaff());
        return apiResponse;
    }
    @GetMapping("/{staffId}")
    public ApiResponse<StaffRespone> getStaffById(@PathVariable("staffId") String staffId) {
        ApiResponse<StaffRespone>  apiResponse = new ApiResponse<>();
        apiResponse.setResult(staffService.getStaffById(staffId));
        return apiResponse;
    }

    @DeleteMapping("/{staffId}")
    public ApiResponse<String> deleteStaffById(@PathVariable("staffId") String staffId) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        staffService.deleteStaff(staffId);
        apiResponse.setResult("Delete staff successfully");
        return apiResponse;
    }
}
