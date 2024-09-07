package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.response.AdminResponse;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.mapper.IAdminMapper;
import com.group07.PetHealthCare.pojo.Admin;
import com.group07.PetHealthCare.respositytory.IAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    IAdminRepository adminRepository;

    @Autowired
    IAdminMapper adminMapper;

    public List<AdminResponse> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        return adminMapper.toAdminResponseList(admins);
    }

    public AdminResponse getAdminById(String id) {
        Admin admin = adminRepository.findById(id).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
        return adminMapper.toAdminResponse(admin);
    }


}
