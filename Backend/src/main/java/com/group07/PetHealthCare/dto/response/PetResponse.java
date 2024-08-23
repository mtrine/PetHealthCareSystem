package com.group07.PetHealthCare.dto.response;

import com.group07.PetHealthCare.pojo.Species;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetResponse {
    private String id;
    private String name;
    private Integer age;
    private Boolean gender;
    private SpeciesResponse speciesResponse;
    private CustomerResponse customerResponse;
}
