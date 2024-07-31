package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Sử dụng InheritanceType.JOINED để lưu các bảng con vào các bảng riêng biệt
@Table(name = "user")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "userID", nullable = false)
    private String id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "phoneNumber", length = 15)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "sex")
    private Boolean sex;

    @Column(name = "password", length = 100)
    private String password;
}
