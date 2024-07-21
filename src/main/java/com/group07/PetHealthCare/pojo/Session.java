package com.group07.PetHealthCare.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @Column(name = "sessionID", nullable = false)
    private Integer id;

    @Column(name = "time")
    private Instant time;

}