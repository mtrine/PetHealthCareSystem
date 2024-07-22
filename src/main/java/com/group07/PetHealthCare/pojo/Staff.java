package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "StaffID", nullable = false)
    private String id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phoneNumber", length = 15)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "sex", length = 10)
    private String sex;

    @Column(name = "isAdmin")
    private Boolean isAdmin;

}