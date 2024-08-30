package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, String> {
}
