package com.group07.PetHealthCare.dto.response;

import jakarta.persistence.Column;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class PaymentResponse {
    private String id;
    private LocalDate paymentDate;
    private String status;
    private String paymentMethod;
    private Double totalAmount;
}
