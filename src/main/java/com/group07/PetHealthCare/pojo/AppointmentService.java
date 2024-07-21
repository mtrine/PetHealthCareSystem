package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "appointment_service")
public class AppointmentService {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointmentID", nullable = false)
    private Appointment appointment;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceID", nullable = false)
    private Service service;

    @Column(name = "quantity")
    private Integer quantity = 0;
}