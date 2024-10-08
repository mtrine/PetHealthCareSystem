package com.group07.PetHealthCare.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ServiceRequest {
    private String name;
    private BigDecimal unitPrice;
}
