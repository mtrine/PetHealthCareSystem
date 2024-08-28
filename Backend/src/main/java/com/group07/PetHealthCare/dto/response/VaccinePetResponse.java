package com.group07.PetHealthCare.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@Builder
public class VaccinePetResponse {
    private VaccineResponse vaccineResponse;
    private PetResponse petResponse;
    private LocalDate stingDate;
    private LocalDate reStingDate;
}
