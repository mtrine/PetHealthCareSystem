package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Sử dụng InheritanceType.JOINED để lưu các bảng con vào các bảng riêng biệt
@Table(name = "payment")
public abstract class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PaymentID", nullable = false)
    private String id;

    @Column(name = "paymentDate")
    private LocalDate paymentDate;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "paymentMethod", length = 50)
    private String paymentMethod;


    @Column(name = "totalAmount")
    private Double totalAmount;


}
