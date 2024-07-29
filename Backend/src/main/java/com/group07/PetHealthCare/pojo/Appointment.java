package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "appointmentID", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sessionID")
    private Session session ;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veterinarianID")
    private Veterinarian veterinarian;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "appointmentDate")
    private Date appointmentDate;

    @Column(name = "deposit", precision = 10, scale = 2)
    private BigDecimal deposit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petID")
    private Pet pet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "serviceID")
    private Services service;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "VeterianSchedulesId")
    private Veterinarianschedule veterinarianschedule;
}