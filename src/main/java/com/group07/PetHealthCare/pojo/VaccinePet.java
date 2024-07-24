package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "vaccine_pet")
public class VaccinePet {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petID", nullable = false)
    private Pet pet;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vaccineID", nullable = false)
    private Vaccine vaccine;

    @Column(name = "stingDate")
    private Date stingDate;

    @Column(name = "reStingDate")
    private Date reStingDate;
}