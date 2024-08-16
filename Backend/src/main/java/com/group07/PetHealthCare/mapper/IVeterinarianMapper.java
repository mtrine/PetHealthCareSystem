package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.request.VeterinarianRequest;
import com.group07.PetHealthCare.dto.response.VeterinarianResponse;
import com.group07.PetHealthCare.pojo.Veterinarian;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IVeterinarianMapper {
    @Mapping(source = "isFulltime", target = "isFulltime")
    VeterinarianResponse toResponse(Veterinarian veterinarian);
    @Mapping(source = "isFulltime", target = "isFulltime")
    List<VeterinarianResponse> toResponseList(List<Veterinarian> veterinarians);
    Veterinarian toVeterinarian(VeterinarianRequest veterinarianRequest);
}
