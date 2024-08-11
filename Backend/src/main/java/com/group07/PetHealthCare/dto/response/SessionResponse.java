package com.group07.PetHealthCare.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class SessionResponse {
    private String id;
    private LocalTime startTime;
    private LocalTime endTime;
}
