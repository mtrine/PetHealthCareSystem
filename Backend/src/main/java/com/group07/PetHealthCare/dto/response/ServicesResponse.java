package com.group07.PetHealthCare.dto.response;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ServicesResponse {
    private String id;
    private String name;
    private BigDecimal unitPrice;
}
