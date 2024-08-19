package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.dto.response.UserResponse;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.mapper.IUserMapper;
import com.group07.PetHealthCare.pojo.Staff;
import com.group07.PetHealthCare.pojo.User;
import com.group07.PetHealthCare.pojo.Veterinarian;
import com.group07.PetHealthCare.respositytory.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private IUserRepository IUserRepository;

    @Autowired
    private IUserMapper userMapper;

    @PostAuthorize("returnObject.email == authentication.name")
    public UserResponse updateInforUser(String id, UserRequest request) {
        User user = IUserRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        // Cập nhật các trường chỉ khi có trong request
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }
        if (request.getOldPassword() != null) {

            if(!request.getOldPassword().equals(user.getPassword())){
                throw new RuntimeException("Mật khẩu cũ sai");
            }
            user.setPassword(request.getPassword());
        }
        if (request.getSex() != null) {
            user.setSex(request.getSex());
        }

        // Cập nhật các trường cụ thể dựa trên loại user
        if (user instanceof Veterinarian) {
            Veterinarian veterinarian = (Veterinarian) user;
            if (request.getIsFulltime() != null) {
                veterinarian.setIsFulltime(request.getIsFulltime());
            }
        }

        return userMapper.toUserRespone(IUserRepository.save(user)) ;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String id) {
        User user = IUserRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        IUserRepository.delete(user);
    }

    public UserResponse getMyInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = IUserRepository.findByEmail(name).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        return userMapper.toUserRespone(user);
    }

    public UserResponse updateMyInfo( UserRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = IUserRepository.findByEmail(name).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        return updateInforUser(user.getId(), request);
    }
}
