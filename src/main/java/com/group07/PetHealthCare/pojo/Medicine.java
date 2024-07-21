package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "medicine")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MedicineID", nullable = false)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "expDate")
    private LocalDate expDate;

    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PrescriptionMedicine> prescriptionMedicines;
}