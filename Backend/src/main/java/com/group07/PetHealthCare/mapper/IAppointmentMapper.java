package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.AppointmentResponse;
import com.group07.PetHealthCare.pojo.Appointment;
import com.group07.PetHealthCare.pojo.AppointmentServices;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IAppointmentMapper {
    @Mapping(source = "veterinarian.name", target = "veterinarianName")
    @Mapping(source = "session.startTime", target = "startTime")
    @Mapping(source = "session.endTime", target = "endTime")
    @Mapping(source = "appointmentServices", target = "serviceName", qualifiedByName = "mapServiceNames")
    AppointmentResponse toAppointmentResponse(Appointment appointment);

    List<AppointmentResponse> toAppointmentResponses(List<Appointment> appointments);
    Appointment toAppointment(AppointmentResponse appointmentResponse);

    @Named("mapServiceNames")
    default Set<String> mapServiceNames(Set<AppointmentServices> appointmentServices) {
        return appointmentServices.stream()
                .map(appointmentService -> appointmentService.getService().getName())
                .collect(Collectors.toSet());
    }
 }
