package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "cages")
public class Cage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cageNumber")
    private Integer cageNumber;

    @Column(name = "status")
    private Boolean status=false;

    @Column(name = "unitPrice")
    private BigDecimal unitPrice;

}