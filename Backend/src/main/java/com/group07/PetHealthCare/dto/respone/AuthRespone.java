package com.group07.PetHealthCare.dto.respone;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRespone {
    private String token;
    private UserRespone userRespone;
}
