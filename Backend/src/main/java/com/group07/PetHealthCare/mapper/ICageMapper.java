package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.CageResponse;
import com.group07.PetHealthCare.pojo.Cage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICageMapper {
    CageResponse toResponse(Cage cage);
    List<CageResponse> toResponseList(List<Cage> cageList);
    Cage toCage(CageResponse cageResponse);
}
