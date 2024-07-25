package com.group07.PetHealthCare.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetCreationRequest {
    private String name;
    private int age;
    private String speciesID;
    private String customerID;

}
