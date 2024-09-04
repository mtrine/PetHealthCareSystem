package com.group07.PetHealthCare.dto.request;

import com.group07.PetHealthCare.enumData.DayOfWeek;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeterinarianScheduleRequest {
    private String status;
    private String sessionId;
    private String veterinarianId;
    private DayOfWeek dayOfWeek;
    private Boolean isPublished;
}