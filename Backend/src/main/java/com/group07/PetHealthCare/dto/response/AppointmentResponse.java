package com.group07.PetHealthCare.dto.response;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@Builder
public class AppointmentResponse {
    private String id;
    private String status;
    private String description;
    private LocalDate appointmentDate;
    private BigDecimal deposit;
    private String veterinarianName;
    private LocalTime startTime;
    private LocalTime endTime;
    private Set<String> serviceName;
    private String petName;
}
