package com.group07.PetHealthCare.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "petID", nullable = false)
    private String id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name="gender")
    private Boolean gender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "speciesID")
    private Species species;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerID")
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VaccinePet> petVaccines = new LinkedHashSet<>();

    @OneToMany(mappedBy = "pet")
    @JsonManagedReference
    private Set<Appointment> appointments= new LinkedHashSet<>();
}