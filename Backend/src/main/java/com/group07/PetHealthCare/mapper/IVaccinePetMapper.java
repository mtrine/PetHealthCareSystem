package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.VaccinePetResponse;
import com.group07.PetHealthCare.pojo.VaccinePet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses={IPetMapper.class,IVaccineMapper.class})
public interface IVaccinePetMapper {
    @Mapping(source = "pet",target = "petResponse")
    @Mapping(source="vaccine",target = "vaccineResponse")
    VaccinePetResponse toVaccinePetResponse(VaccinePet vaccinePet);
    VaccinePet toVaccinePet(VaccinePet vaccinePet);
    List<VaccinePetResponse> toVaccinePetResponseList(List<VaccinePet> vaccinePets);
}
