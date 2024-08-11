package com.group07.PetHealthCare.dto.response;


import com.group07.PetHealthCare.pojo.Appointment;
import com.group07.PetHealthCare.pojo.Services;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class AppointmentServicesResponse {
    private String appointmentServiceId;
    private int quantity;
}
