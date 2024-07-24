package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customerID", nullable = false)
    private String id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phoneNumber", length = 15)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "sex", length = 10)
    private String sex;

    @Column(name = "password", length = 100)
    private String password;

    @OneToMany(mappedBy = "customer")
    private Set<Pet> pets = new LinkedHashSet<>();

    @OneToMany(mappedBy = "customer")
    private Set<Reviews> reviews = new LinkedHashSet<>();


}