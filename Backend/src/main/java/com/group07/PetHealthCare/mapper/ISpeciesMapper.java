package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.SpeciesResponse;
import com.group07.PetHealthCare.pojo.Species;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISpeciesMapper {
    SpeciesResponse toSpeciesResponse(Species species);
    Species toSpecies(SpeciesResponse speciesResponse);
    List<SpeciesResponse> toSpeciesResponseList(List<Species> speciesList);
}
