package com.group07.PetHealthCare.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewsRequest {
    private String userId;
    private String appointmentId;
    private int grades;
    private String comments;
    private LocalDate reviewDate;
}
