package com.group07.PetHealthCare.pojo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "veterinarian")
public class Veterinarian extends User {
    @Column(name = "isFulltime")
    private Boolean isFulltime=false;
}
