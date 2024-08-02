package com.group07.PetHealthCare.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Set;


@Getter
@Setter
public class AppointmentRequest {
    private String status;
    private Date appointmentDate;
    private BigDecimal deposit;
    private String petId;
    private Set<String> serviceId;
    private String veterinarianId;
    private String sessionId;
    private String description;

}