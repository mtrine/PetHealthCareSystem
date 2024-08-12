package com.group07.PetHealthCare.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
public class VaccineResponse {
    private String id;
    private String name;
    private LocalDate expDate;
}
