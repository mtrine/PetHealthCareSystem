package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.ServicesResponse;
import com.group07.PetHealthCare.pojo.Services;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IServiceMapper {
    ServicesResponse toServicesResponse(Services services);
    Services toServices(ServicesResponse servicesResponse);
    List<ServicesResponse> toServicesResponseList(List<Services> services);
}
