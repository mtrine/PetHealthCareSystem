package com.group07.PetHealthCare.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class VaccinePetRequest {
    private String petId;
    private String vaccineId;
    private LocalDate stingDate;
    private LocalDate reStingDate;
}
