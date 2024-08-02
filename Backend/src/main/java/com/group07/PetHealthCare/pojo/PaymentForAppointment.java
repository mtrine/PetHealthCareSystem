package com.group07.PetHealthCare.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "payment_for_appointment")
public class PaymentForAppointment extends Payment{
    @OneToOne(fetch = FetchType.EAGER)
    private Appointment appointment;
}
