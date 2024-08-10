package com.group07.PetHealthCare.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "vaccine")
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "VaccineID", nullable = false)
    private String id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "expDate")
    private LocalDate expDate;

    @OneToMany(mappedBy = "vaccine", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<VaccinePet> petVaccines = new HashSet<>();
}