package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.AppointmentResponse;
import com.group07.PetHealthCare.pojo.Appointment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAppointmentMapper {
    AppointmentResponse toAppointmentResponse(Appointment appointment);
    List<AppointmentResponse> toAppointmentResponses(List<Appointment> appointments);
    Appointment toAppointment(AppointmentResponse appointmentResponse);
 }
