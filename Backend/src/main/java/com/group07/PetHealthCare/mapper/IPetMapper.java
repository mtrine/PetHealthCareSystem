package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.PetResponse;
import com.group07.PetHealthCare.pojo.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {ISpeciesMapper.class,ICustomerMapper.class})
public interface IPetMapper {
    @Mapping(source = "pet.species", target = "speciesResponse")
    @Mapping(source = "pet.customer",target="customerResponse")
    PetResponse toResponse(Pet pet);
    Pet toPet(Pet pet);
    Set<PetResponse> toResponseList(Set<Pet> pets);
}
