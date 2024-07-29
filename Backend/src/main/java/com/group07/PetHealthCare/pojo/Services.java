package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "services")
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "serviceID", nullable = false)
    private String id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "unitPrice", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Appointment> appointments= new LinkedHashSet<>();
}