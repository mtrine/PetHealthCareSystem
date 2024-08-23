package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.HospitalizationResponse;
import com.group07.PetHealthCare.pojo.Hospitalization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses = {IPetMapper.class,ICageMapper.class})
public interface IHospitalizationMapper {
    @Mapping(source="petID",target = "petResponse")
    @Mapping(source="cage",target = "cageResponse")
    HospitalizationResponse toHospitalizationResponse(Hospitalization hospitalization);
    List<HospitalizationResponse> toHospitalizationResponseList(List<Hospitalization> hospitalizationList);
    Hospitalization toHospitalization(HospitalizationResponse hospitalizationResponse);
}
