package com.group07.PetHealthCare.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "species")
public class Species {
    @Id
    @Column(name = "speciesID", nullable = false)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

}