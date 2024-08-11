package com.group07.PetHealthCare.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class VaccineResponse {
    private String id;
    private String name;
    private LocalDate expDate;
}
