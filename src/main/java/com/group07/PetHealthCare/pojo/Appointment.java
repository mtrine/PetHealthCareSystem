package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointmentID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessionID")
    private Session session ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinarianID")
    private Veterinarian veterinarian;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "deposit", precision = 10, scale = 2)
    private BigDecimal deposit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petID")
    private Pet pet;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AppointmentService> appointmentServices;
}