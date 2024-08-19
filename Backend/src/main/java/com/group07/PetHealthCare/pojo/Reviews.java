package com.group07.PetHealthCare.pojo;

import com.group07.PetHealthCare.config.ReviewsId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Reviews {
    @EmbeddedId
    private ReviewsId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerID", insertable = false, updatable = false)
    private Customer customer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointmentID", insertable = false, updatable = false)
    private Appointment appointment;

    @Column(name = "Grades")
    private Integer grades;

    @Column(name = "comment")
    private String comment;

    @Column(name = "reviewDate")
    private LocalDate reviewDate;
}
