package com.group07.PetHealthCare.dto.request;

import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.pojo.Species;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetCreationRequest {

    private String name;
    private Integer age;
    private String speciesID;
    private String customerID;
}
