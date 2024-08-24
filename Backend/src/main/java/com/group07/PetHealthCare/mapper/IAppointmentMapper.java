package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.AppointmentResponse;
import com.group07.PetHealthCare.dto.response.ServicesResponse;
import com.group07.PetHealthCare.pojo.Appointment;
import com.group07.PetHealthCare.pojo.AppointmentServices;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",uses = {IPetMapper.class,IServiceMapper.class})
public interface IAppointmentMapper {
    @Mapping(source = "veterinarian.name", target = "veterinarianName")
    @Mapping(source = "session", target = "sessionResponse")
    @Mapping(source = "pet",target="pet")
    @Mapping(source = "appointmentServices", target = "servicesResponsesList", qualifiedByName = "mapServiceResponse")
    AppointmentResponse toAppointmentResponse(Appointment appointment);

    List<AppointmentResponse> toAppointmentResponses(List<Appointment> appointments);
    Appointment toAppointment(AppointmentResponse appointmentResponse);

    @Named("mapServiceResponse")
    default Set<ServicesResponse> mapServiceResponse(Set<AppointmentServices> appointmentServices) {
        return appointmentServices.stream()
                .map(appointmentService -> {
                    ServicesResponse servicesResponse = new ServicesResponse();
                    servicesResponse.setId(appointmentService.getService().getId());
                    servicesResponse.setName(appointmentService.getService().getName());
                    servicesResponse.setUnitPrice(appointmentService.getService().getUnitPrice());
                    return servicesResponse;
                })
                .collect(Collectors.toSet());
    }
 }
