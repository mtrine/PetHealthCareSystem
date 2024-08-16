package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.response.StaffResponse;
import com.group07.PetHealthCare.mapper.IStaffMapper;
import com.group07.PetHealthCare.pojo.Staff;
import com.group07.PetHealthCare.respositytory.IStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    IStaffRepository staffRepository;

    @Autowired
    IStaffMapper staffMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public List<StaffResponse> getAllStaff() {

        return staffMapper.toResponeList(staffRepository.findAll());
    }

    public StaffResponse getStaffById(String staff) {
        return staffMapper.toRespone(staffRepository.findById(staff).orElseThrow(()->new RuntimeException("Not find")));
    }


}
