package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findAllByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT p FROM Payment p WHERE YEAR(p.paymentDate) = :year")
    List<Payment> findAllByPaymentDateYear(@Param("year") int year);

    @Query("SELECT p FROM Payment p WHERE YEAR(p.paymentDate) BETWEEN :startYear AND :endYear")
    List<Payment> findAllByPaymentDateYearBetween(@Param("startYear") int startYear, @Param("endYear") int endYear);
}
