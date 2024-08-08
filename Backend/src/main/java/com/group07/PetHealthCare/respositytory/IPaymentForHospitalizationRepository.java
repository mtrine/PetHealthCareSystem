package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.PaymentForHospitalization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface IPaymentForHospitalizationRepository extends JpaRepository<PaymentForHospitalization, String> {
}
