package com.group07.PetHealthCare.config;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class VaccinePetId implements Serializable {

    @Column(name = "petID")
    private String petId;

    @Column(name = "vaccineID")
    private String vaccineId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VaccinePetId that = (VaccinePetId) o;
        return Objects.equals(petId, that.petId) &&
                Objects.equals(vaccineId, that.vaccineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, vaccineId);
    }
}
