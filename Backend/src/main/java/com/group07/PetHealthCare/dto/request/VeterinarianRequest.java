package com.group07.PetHealthCare.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeterinarianRequest extends UserRequest {
    private boolean isFullTime;
}
