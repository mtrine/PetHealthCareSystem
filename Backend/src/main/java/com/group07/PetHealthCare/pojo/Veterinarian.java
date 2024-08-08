package com.group07.PetHealthCare.pojo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "veterinarian")
public class Veterinarian extends User {
    @Column(name = "isFulltime")
    private Boolean isFulltime=false;
    @OneToMany(mappedBy = "veterinarian")
    @JsonBackReference
    private Set<Appointment> appointments=new LinkedHashSet<>();

    @OneToMany(mappedBy = "veterinarian")
    private List<VisitSchedule> visitSchedules;
}
