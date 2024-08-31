package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.PaymentForAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPaymentForAppointmentRepository extends JpaRepository<PaymentForAppointment, String> {
    Optional<PaymentForAppointment> findByAppointmentId(String appointmentId);
}
