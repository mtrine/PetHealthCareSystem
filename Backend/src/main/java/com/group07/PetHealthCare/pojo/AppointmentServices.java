package com.group07.PetHealthCare.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "appointment_service")
public class AppointmentServices {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "appointmentServiceID", nullable = false)
    private String appointmentServiceId;

    @ManyToOne
    @JoinColumn(name = "appointmentId", nullable = false)
    @JsonIgnore
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "serviceId", nullable = false)
    private Services service;

    @Column(name = "quantity")
    private int quantity;
}
