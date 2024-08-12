package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.PetResponse;
import com.group07.PetHealthCare.pojo.Pet;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface IPetMapper {
    PetResponse toResponse(Pet pet);
    Pet toPet(Pet pet);
    Set<PetResponse> toResponseList(Set<Pet> pets);
}
