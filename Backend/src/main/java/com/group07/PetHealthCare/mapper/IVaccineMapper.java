package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.VaccineResponse;
import com.group07.PetHealthCare.pojo.Vaccine;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IVaccineMapper {
    VaccineResponse toVaccineResponse(Vaccine vaccine);
    Vaccine toVaccine(VaccineResponse vaccineResponse);
    List<VaccineResponse> toVaccineResponses(List<Vaccine> vaccines);
}
