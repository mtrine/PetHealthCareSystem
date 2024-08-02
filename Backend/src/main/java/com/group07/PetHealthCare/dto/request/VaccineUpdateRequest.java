
package com.group07.PetHealthCare.dto.request;

import java.time.LocalDate;

public class VaccineUpdateRequest {

    private LocalDate expdate;



    public LocalDate getExpdate() {
        return expdate;
    }

    public void setExpdate(LocalDate expdate) {
        this.expdate = expdate;
    }
}
