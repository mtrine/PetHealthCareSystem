package com.group07.PetHealthCare.config;

import com.group07.PetHealthCare.pojo.Appointment;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewsId implements Serializable {
    @Column(name="customerID")
    private String customerID;
    @Column(name = "appointmentID")
    private String appointmentID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewsId that = (ReviewsId) o;
        return Objects.equals(customerID, that.customerID) &&
                Objects.equals(appointmentID, that.appointmentID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerID, appointmentID);
    }

}
