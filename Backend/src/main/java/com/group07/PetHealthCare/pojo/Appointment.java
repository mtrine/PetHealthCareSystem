package com.group07.PetHealthCare.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "appointmentID", nullable = false)
    private String id;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "description")
    private String description;

    @Column(name = "appointmentDate")
    private Date appointmentDate;

    @Column(name = "deposit", precision = 10, scale = 2)
    private BigDecimal deposit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petID")
    @JsonBackReference
    private Pet pet;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AppointmentServices> appointmentServices = new LinkedHashSet<>();


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veterinarianID")
    @JsonManagedReference
    private Veterinarian veterinarian;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sessionId")
    private Session session;
}