package com.group07.PetHealthCare.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class HospitalizationResponse {
    private String id;
    private String reasonForHospitalization;
    private String healthCondition;
    private LocalDate startDate;
    private LocalDate endDate;
}
