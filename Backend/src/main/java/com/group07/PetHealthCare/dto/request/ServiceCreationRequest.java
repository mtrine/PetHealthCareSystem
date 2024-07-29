package com.group07.PetHealthCare.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ServiceCreationRequest {
    private String name;
    private BigDecimal unit_price;
}
