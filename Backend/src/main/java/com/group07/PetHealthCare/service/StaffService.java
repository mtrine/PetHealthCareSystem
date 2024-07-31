package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.pojo.Staff;
import com.group07.PetHealthCare.respositytory.IStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    IStaffRepository staffRepository;

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(String staff) {
        return staffRepository.findById(staff).get();
    }

    public void deleteStaff(String staffId) {
        Staff staff = staffRepository.findById(staffId).orElseThrow(()->new RuntimeException("Staff not found"))   ;
        staffRepository.delete(staff);
    }
}
