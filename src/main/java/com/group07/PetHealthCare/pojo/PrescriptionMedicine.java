package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "prescription_medicine")
public class PrescriptionMedicine {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescriptionID",nullable = false)
    private Prescription prescription;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicineID",nullable = false)
    private Medicine medicine;

    @Column(name = "quantity")
    private Integer quantity = 0;
}