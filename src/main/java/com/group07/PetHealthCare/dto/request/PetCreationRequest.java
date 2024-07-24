package com.group07.PetHealthCare.dto.request;

import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.pojo.Species;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetCreationRequest {

    @Getter
    private String name;
    private int age;
    private String speciesID;
    private String customerID;

    // Getters and Setters

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpeciesID() {
        return speciesID;
    }

    public void setSpeciesID(String speciesID) {
        this.speciesID = speciesID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

}
