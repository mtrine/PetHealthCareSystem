package com.group07.PetHealthCare.pojo;

import com.group07.PetHealthCare.config.VaccinePetId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "vaccine_pet")
public class VaccinePet {

    @EmbeddedId
    private VaccinePetId id= new VaccinePetId();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("vaccineId")
    @JoinColumn(name = "vaccineID", nullable = false)
        private Vaccine vaccine;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("petId")
    @JoinColumn(name = "petID", nullable = false)
    private Pet pet;

    @Column(name = "stingDate")
    private LocalDate stingDate;

    @Column(name = "reStingDate")
    private LocalDate reStingDate;
}
