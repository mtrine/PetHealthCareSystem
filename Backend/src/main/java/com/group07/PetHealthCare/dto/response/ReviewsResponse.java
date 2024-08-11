package com.group07.PetHealthCare.dto.response;

import com.group07.PetHealthCare.pojo.Appointment;
import com.group07.PetHealthCare.pojo.Customer;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder

public class ReviewsResponse {
    private CustomerResponse customerresponse;
    private Integer grades;
    private String comment;
    private Date reviewDate;
}
