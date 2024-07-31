package com.group07.PetHealthCare.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @Size(min = 2, message = "NAME_INVALID")
    private String name;

    @Email(message = "EMAIL_INVALID")
    private String email;
    private String phoneNumber;
    private String address;
    private Boolean sex;
    @Size(min = 6, message = "PASS_INVALID")
    private String password;
    private String role;
    // Fields specific to Veterinarian
    private Boolean isFulltime;
    // Fields specific to Staff
    private Boolean isAdmin;

}
