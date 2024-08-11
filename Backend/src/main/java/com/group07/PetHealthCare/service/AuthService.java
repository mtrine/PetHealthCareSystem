package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.dto.response.AuthResponse;
import com.group07.PetHealthCare.dto.response.UserResponse;
import com.group07.PetHealthCare.enumData.Role;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.mapper.IUserMapper;
import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.pojo.Staff;
import com.group07.PetHealthCare.pojo.User;
import com.group07.PetHealthCare.pojo.Veterinarian;
import com.group07.PetHealthCare.respositytory.ICustomerRepository;
import com.group07.PetHealthCare.respositytory.IStaffRepository;
import com.group07.PetHealthCare.respositytory.IUserRepository;
import com.group07.PetHealthCare.respositytory.IVeterinarianRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {
    private Dotenv dotenv = Dotenv.load();
    @Autowired
    private IUserRepository IUserRepository;
    @Autowired
    private ICustomerRepository ICustomerRepository;
    @Autowired
    private IVeterinarianRepository IVeterinarianRepository;
    @Autowired
    private IStaffRepository IStaffRepository;

    @Autowired
    private IUserMapper userMapper;

    public UserResponse register(UserRequest request) {
        Optional<User> existingCustomer = IUserRepository.findByEmail(request.getEmail());
        if (existingCustomer.isPresent()) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // Create a new user based on role
        User newUser;
        switch (request.getRole()) {
            case "CUSTOMER":
                newUser = new Customer();
                break;
            case "STAFF":
                newUser = new Staff();
                break;
            case "VETERINARIAN":
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
        newUser.setRole(Role.valueOf(request.getRole()));

        // Save user based on role
        if (newUser instanceof Customer) {
            return userMapper.toUserRespone(ICustomerRepository.save((Customer) newUser)) ;
        } else if (newUser instanceof Staff) {
            return userMapper.toUserRespone(IStaffRepository.save((Staff) newUser));
        } else {
            return userMapper.toUserRespone(IVeterinarianRepository.save((Veterinarian) newUser));
        }
    }

    public AuthResponse login(UserRequest request) throws JOSEException {
        User user;
        user =IUserRepository.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("User not found"));
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            throw new AppException(ErrorCode.INCORRECT_EMAIL_OR_PASSWORD);
        }

        String token =generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .userRespone(userMapper.toUserRespone(user))
                .build();
    }

    private String generateToken(User user) throws JOSEException {
        JWSHeader header=new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet=new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli())
                )
                .claim("customClaim","Custom")
                .claim("userId", user.getId())
                .build();

        Payload payload =new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject=new JWSObject(header,payload);
        try {
            jwsObject.sign(new MACSigner(dotenv.get("ACCESS_TOKEN_KEY").getBytes()));
            return jwsObject.serialize();
        }catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
