package com.group07.PetHealthCare.dto.request;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshRequest {
    private String token;
}
