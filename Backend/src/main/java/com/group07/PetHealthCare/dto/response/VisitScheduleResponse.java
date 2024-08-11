package com.group07.PetHealthCare.dto.response;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VisitScheduleResponse{
    private String visitScheduleId;
    private LocalDate visitDate;
}
