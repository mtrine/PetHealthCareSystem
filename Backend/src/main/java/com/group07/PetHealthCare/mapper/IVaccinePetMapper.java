package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.VaccinePetResponse;
import com.group07.PetHealthCare.pojo.VaccinePet;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IVaccinePetMapper {
    VaccinePetResponse toVaccinePetResponse(VaccinePet vaccinePet);
    VaccinePet toVaccinePet(VaccinePet vaccinePet);
    List<VaccinePetResponse> toVaccinePetResponseList(List<VaccinePet> vaccinePets);
}
