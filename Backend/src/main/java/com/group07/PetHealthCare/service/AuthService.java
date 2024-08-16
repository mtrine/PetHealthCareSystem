package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.IntrospectRequest;
import com.group07.PetHealthCare.dto.request.LogoutRequest;
import com.group07.PetHealthCare.dto.request.RefreshRequest;
import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.dto.response.AuthResponse;
import com.group07.PetHealthCare.dto.response.IntrospectRespone;
import com.group07.PetHealthCare.dto.response.UserResponse;
import com.group07.PetHealthCare.enumData.Role;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.mapper.IUserMapper;
import com.group07.PetHealthCare.pojo.*;
import com.group07.PetHealthCare.respositytory.*;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.ref.Reference;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private IUserRepository IUserRepository;
    @Autowired
    private ICustomerRepository ICustomerRepository;
    @Autowired
    private IVeterinarianRepository IVeterinarianRepository;
    @Autowired
    private IStaffRepository IStaffRepository;
    @Autowired
    private IInvalidatedTokenRepository invalidatedTokenRepository;
    @Autowired
    private IUserMapper userMapper;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;
    @Autowired
    private UserService userService;


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
            case "ADMIN":
                newUser = new Admin();
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

    public AuthResponse login(UserRequest request)  {
        User user;
        user =IUserRepository.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("User not found"));
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            throw new AppException(ErrorCode.INCORRECT_EMAIL_OR_PASSWORD);
        }

        String token=generateToken(user);
        return AuthResponse.builder()
                .token(token)
                .userResponse(userMapper.toUserRespone(user))
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("localhost:8080")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .claim("userId", user.getId())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        return user.getRole().name();
    }

   public IntrospectRespone introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        boolean isValid=true;

       try{
           verifyToken(token,false);
       }catch (AppException e) {
            isValid=false;
       }
       return IntrospectRespone.builder()
               .valid(isValid)
               .build();
   }

   public AuthResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
       SignedJWT  signJWT = verifyToken(request.getToken(),true);
       String jit = signJWT.getJWTClaimsSet().getJWTID();
       Date expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();
       InvalidatedToken invalidatedToken =
               InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

       invalidatedTokenRepository.save(invalidatedToken);
        String email =signJWT.getJWTClaimsSet().getSubject();
        User user = IUserRepository.findByEmail(email).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
        String token =generateToken(user);
       return AuthResponse.builder()
               .token(token)
               .userResponse(userMapper.toUserRespone(user))
               .build();
   }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    public void logout(LogoutRequest request){

    }
}
