




package com.group07.PetHealthCare.dto.request;

import java.time.LocalDate;

public class VaccineCreationRequest {
    private String name;
    private LocalDate expdate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getExpdate() {
        return expdate;
    }

    public void setExpdate(LocalDate expdate) {
        this.expdate = expdate;
    }
}
