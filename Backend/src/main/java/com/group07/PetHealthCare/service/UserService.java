package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.pojo.Staff;
import com.group07.PetHealthCare.pojo.User;
import com.group07.PetHealthCare.pojo.Veterinarian;
import com.group07.PetHealthCare.respositytory.CustomerRepository;
import com.group07.PetHealthCare.respositytory.StaffRepository;
import com.group07.PetHealthCare.respositytory.VeterinarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VeterinarianRepository veterinarianRepository;
    @Autowired
    private StaffRepository staffRepository;

    public User register(UserRequest request) {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(request.getEmail());
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
            return customerRepository.save((Customer) newUser);
        } else if (newUser instanceof Staff) {
            return staffRepository.save((Staff) newUser);
        } else {
            return veterinarianRepository.save((Veterinarian) newUser);
        }
    }

    public User login(UserRequest request) {
        User user;
        switch (request.getRole()) {
            case "Customer":
                user= customerRepository.findByEmail(request.getEmail()).get();
                break;
            case "Staff":
                user = staffRepository.findByEmail(request.getEmail()).get();
                break;
            case "Veterinarian":
                user = veterinarianRepository.findByEmail(request.getEmail()).get();
                break;
            default:
                throw new AppException(ErrorCode.INVALID_ROLE);
        }

        if (user==null ||  !user.getPassword().equals(request.getPassword())) {
            throw new AppException(ErrorCode.INCORRECT_EMAIL_OR_PASSWORD);
        }
        return user;
    }
}
