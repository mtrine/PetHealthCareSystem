package com.group07.PetHealthCare.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class VisitScheduleRequest {
    private String veterinarianId;
    private String hospitalizationId;
    private LocalDate visitDate;
    private String sessionId;
}
