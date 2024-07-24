package com.group07.PetHealthCare.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
public class AppointmentCreationRequest {
    private String sessionId;
    private String veterinarianId;
    private String status;
    private Date appointmentDate;
    private BigDecimal deposit;
    private String petId;
}
