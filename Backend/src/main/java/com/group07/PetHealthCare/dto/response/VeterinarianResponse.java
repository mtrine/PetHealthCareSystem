package com.group07.PetHealthCare.dto.response;

import com.group07.PetHealthCare.dto.response.UserResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VeterinarianResponse extends UserResponse{
    private Boolean isFulltime;
}
