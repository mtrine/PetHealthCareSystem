package com.group07.PetHealthCare.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HospitalizationResponse {
    private String id;
    private String reasonForHospitalization;
    private String healthCondition;
    private LocalDate startDate;
    private LocalDate endDate;
    private PetResponse petResponse;
    private CageResponse cageResponse;
}
