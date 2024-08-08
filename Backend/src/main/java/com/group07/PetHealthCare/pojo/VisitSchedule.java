package com.group07.PetHealthCare.pojo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "visit_schedule")
@Setter
@Getter
public class VisitSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String visitScheduleId;

    @ManyToOne
    @JoinColumn(name = "veterinarian_id", nullable = false)
    private Veterinarian veterinarian;

    @ManyToOne
    @JoinColumn(name = "hospitalization_id", nullable = false)
    private Hospitalization hospitalization;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sessionId")
    private Session session;
}