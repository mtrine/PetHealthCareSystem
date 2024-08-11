package com.group07.PetHealthCare.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpeciesResponse {
    private String id;
    private String name;
}
