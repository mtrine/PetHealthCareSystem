package com.group07.PetHealthCare.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder

public class CageResponse {
    private Integer cageNumber;
    private Boolean status=false;
    private BigDecimal unitPrice;
}
