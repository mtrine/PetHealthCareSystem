package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.PaymentForAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentForAppointmentRepository extends JpaRepository<PaymentForAppointment, String> {
}
