package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "hospitalization")
public class Hospitalization {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "HospitalizationID", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petID")
    private Pet petID;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cageNumber", unique = false)
    private Cage cage;

    @Column(name="reason_for_hospitalization")
    private String reasonForHospitalization;

    @Column(name = "healthCondition")
    private String healthCondition;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;
}
