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
    @Column(name = "cageNumber")
    private Integer cageNumber;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "unitPrice")
    private Double unitPrice;

}