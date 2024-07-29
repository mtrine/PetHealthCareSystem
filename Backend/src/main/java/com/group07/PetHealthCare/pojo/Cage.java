package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cages")
public class Cage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cageID", nullable = false)
    private String id;

    @Column(name = "numberCage")
    private Integer numberCage;

    @Column(name = "status", length = 50)
    private String status;

}