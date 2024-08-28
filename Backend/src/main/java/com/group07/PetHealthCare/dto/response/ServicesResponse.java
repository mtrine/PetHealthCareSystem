package com.group07.PetHealthCare.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServicesResponse {
    private String id;
    private String name;
    private BigDecimal unitPrice;
}
