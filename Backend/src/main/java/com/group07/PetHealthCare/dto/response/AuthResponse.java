package com.group07.PetHealthCare.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private UserResponse userResponse;
}
