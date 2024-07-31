package com.group07.PetHealthCare.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetCreationRequest {
    private String name;
    private int age;
    private Boolean gender;
    private String speciesID;
    private String customerID;

}
