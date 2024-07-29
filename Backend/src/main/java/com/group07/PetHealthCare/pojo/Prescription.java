package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "prescription")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PrescriptionID", nullable = false)
    private String id;

    @Column(name = "prescriptionDate")
    private LocalDate prescriptionDate;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veterinarianID")
    private Veterinarian veterinarianID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petID")
    private Pet petID;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PrescriptionMedicine> prescriptionMedicines;

}