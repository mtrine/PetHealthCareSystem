package com.group07.PetHealthCare.dto.response;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class AppointmentResponse {
    private String id;
    private String status;
    private String description;
    private LocalDate appointmentDate;
    private BigDecimal deposit;
}
