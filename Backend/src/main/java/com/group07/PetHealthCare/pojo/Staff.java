package com.group07.PetHealthCare.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "staff")
public class Staff extends User {

    @Column(name = "isAdmin")
    private Boolean isAdmin=false;
}
