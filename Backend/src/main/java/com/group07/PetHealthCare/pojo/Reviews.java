package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Reviews {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerID",nullable = false)
    private Customer customer;

    @Id
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointmentID",nullable = false)
    private Appointment appointment;

    @Column(name = "Grades" )
    private Integer grades;

    @Column(name = "comment")
    private String comment;

    @Column(name="reviewDate")
    private Date reviewDate;
}