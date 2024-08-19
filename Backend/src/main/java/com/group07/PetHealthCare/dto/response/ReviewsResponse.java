package com.group07.PetHealthCare.dto.response;

import com.group07.PetHealthCare.pojo.Appointment;
import com.group07.PetHealthCare.pojo.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsResponse {
    private CustomerResponse customerresponse;
    private AppointmentResponse appointmentresponse;
    private Integer grades;
    private String comment;
    private LocalDate reviewDate;
}
