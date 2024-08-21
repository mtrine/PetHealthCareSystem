package com.group07.PetHealthCare.dto.response;

import com.group07.PetHealthCare.pojo.Species;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PetResponse {
    private String id;
    private String name;
    private Integer age;
    private Boolean gender;
    private SpeciesResponse speciesResponse;
    private CustomerResponse customerResponse;
}
