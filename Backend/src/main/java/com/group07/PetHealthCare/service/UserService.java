package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.pojo.Staff;
import com.group07.PetHealthCare.pojo.User;
import com.group07.PetHealthCare.pojo.Veterinarian;
import com.group07.PetHealthCare.respositytory.ICustomerRepository;
import com.group07.PetHealthCare.respositytory.IStaffRepository;
import com.group07.PetHealthCare.respositytory.IUserRepository;
import com.group07.PetHealthCare.respositytory.IVeterinarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private IUserRepository IUserRepository;
    @Autowired
    private ICustomerRepository ICustomerRepository;
    @Autowired
    private IVeterinarianRepository IVeterinarianRepository;
    @Autowired
    private IStaffRepository IStaffRepository;

    public User register(UserRequest request) {
        Optional<User> existingCustomer = IUserRepository.findByEmail(request.getEmail());
        if (existingCustomer.isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // Create a new user based on role
        User newUser;
        switch (request.getRole()) {
            case "Customer":
                newUser = new Customer();
                break;
            case "Staff":
                newUser = new Staff();
                break;
            case "Veterinarian":
                newUser = new Veterinarian();
                break;
            default:
                throw new AppException(ErrorCode.INVALID_ROLE);
        }

        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setAddress(request.getAddress());
        newUser.setSex(request.getSex());
        newUser.setPassword(request.getPassword());

        // Save user based on role
        if (newUser instanceof Customer) {
            return ICustomerRepository.save((Customer) newUser);
        } else if (newUser instanceof Staff) {
            return IStaffRepository.save((Staff) newUser);
        } else {
            return IVeterinarianRepository.save((Veterinarian) newUser);
        }
    }

    public User login(UserRequest request) {
        User user;
        switch (request.getRole()) {
            case "Customer":
                user= ICustomerRepository.findByEmail(request.getEmail()).get();
                break;
            case "Staff":
                user = IStaffRepository.findByEmail(request.getEmail()).get();
                break;
            case "Veterinarian":
                user = IVeterinarianRepository.findByEmail(request.getEmail()).get();
                break;
            default:
                throw new AppException(ErrorCode.INVALID_ROLE);
        }

        if (user==null ||  !user.getPassword().equals(request.getPassword())) {
            throw new AppException(ErrorCode.INCORRECT_EMAIL_OR_PASSWORD);
        }
        return user;
    }

    public User updateInforUser(String id, UserRequest request) {
        User user = IUserRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        // Update only the fields present in the request
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }
        if (request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }
        if (request.getSex() != null) {
            user.setSex(request.getSex());
        }

        // Save the updated user
        return IUserRepository.save(user);
    }

}
