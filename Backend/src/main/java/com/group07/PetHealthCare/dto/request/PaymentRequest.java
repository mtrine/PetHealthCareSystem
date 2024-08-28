package com.group07.PetHealthCare.dto.request;

import com.group07.PetHealthCare.enumData.TYPEPAYMENT;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    private LocalDate paymentDate;
    private String status;
    private String paymentMethod;
    private String typePayment;
    private Double totalAmount;
    private String appointmentId;
    private String hospitalizationId;
}
