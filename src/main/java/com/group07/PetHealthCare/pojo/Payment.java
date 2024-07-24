package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PaymentID", nullable = false)
    private String id;

    @Column(name = "paymentDate")
    private LocalDate paymentDate;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "paymentMethod", length = 50)
    private String paymentMethod;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointmentID")
    private Appointment appointment;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prescriptionID")
    private Prescription prescription;
}