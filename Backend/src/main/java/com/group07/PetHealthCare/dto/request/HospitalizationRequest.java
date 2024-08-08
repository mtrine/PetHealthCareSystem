package com.group07.PetHealthCare.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class HospitalizationRequest {
    private String petID;
    private Integer cageNumber;
    private String reasonForHospitalization;
    private String healthCondition;
    private LocalDate startDate;
    private LocalDate endDate;
}
