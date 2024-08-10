package com.group07.PetHealthCare.dto.respone;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserRespone {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Boolean sex;
    private String password;
    private String role;
}
