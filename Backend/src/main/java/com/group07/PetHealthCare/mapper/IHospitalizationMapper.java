package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.HospitalizationResponse;
import com.group07.PetHealthCare.pojo.Hospitalization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IHospitalizationMapper {
    @Mapping(source="petID.name",target = "petName")
    HospitalizationResponse toHospitalizationResponse(Hospitalization hospitalization);
    List<HospitalizationResponse> toHospitalizationResponseList(List<Hospitalization> hospitalizationList);
    Hospitalization toHospitalization(HospitalizationResponse hospitalizationResponse);
}
