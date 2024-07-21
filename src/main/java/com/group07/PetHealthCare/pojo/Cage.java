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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cageID", nullable = false)
    private Integer id;

    @Column(name = "numberCage")
    private Integer numberCage;

    @Column(name = "status", length = 50)
    private String status;

}