package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.response.StaffResponse;
import com.group07.PetHealthCare.mapper.IStaffMapper;
import com.group07.PetHealthCare.pojo.Staff;
import com.group07.PetHealthCare.respositytory.IStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    IStaffRepository staffRepository;

    @Autowired
    IStaffMapper staffMapper;
    public List<StaffResponse> getAllStaff() {

        return staffMapper.toResponeList(staffRepository.findAll());
    }

    public StaffResponse getStaffById(String staff) {
        return staffMapper.toRespone(staffRepository.findById(staff).get());
    }

    public void deleteStaff(String staffId) {
        Staff staff = staffRepository.findById(staffId).orElseThrow(()->new RuntimeException("Staff not found"))   ;
        staffRepository.delete(staff);
    }
}
