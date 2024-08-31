package com.group07.PetHealthCare.dto.request;

import lombok.*;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitScheduleRequest {
    private String veterinarianId;
    private String hospitalizationId;
    private LocalDate visitDate;
    private Boolean status;
    private int sessionId;


}
