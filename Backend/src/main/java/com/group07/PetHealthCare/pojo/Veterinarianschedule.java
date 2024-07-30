package com.group07.PetHealthCare.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.group07.PetHealthCare.enumData.DayOfWeek;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "veterianschedules")
public class Veterinarianschedule {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "VeterinarianSchedulesId", nullable = false)
    private String veterianSchedulesId;

    @Column(name = "status", length = 50)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sessionId", nullable = false)
    private Session session;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "VeterinarianId", nullable = false)
    @JsonManagedReference
    private Veterinarian veterinarian;

    @Enumerated(EnumType.STRING)
    @Column(name = "dayOfWeek", nullable = false, length = 10)
    private DayOfWeek dayOfWeek;

    @Column(name = "isPublished", nullable = false)
    @ColumnDefault("false")
    private Boolean isPublished;

    @OneToOne(mappedBy = "veterinarianschedule")
    @JsonBackReference
    private Appointment appointment;
}