package com.group07.PetHealthCare.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetCreationRequest {
    private String name;
    private int age;
    private Boolean gender;
    private String speciesID;
    private String customerID;

}
