package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "veterinarian")
public class Veterinarian extends User {

    @Column(name = "isFulltime")
    private Boolean isFulltime=false;

    @OneToMany(mappedBy = "veterinarian")
    private Set<Appointment> appointments = new LinkedHashSet<>();
}
