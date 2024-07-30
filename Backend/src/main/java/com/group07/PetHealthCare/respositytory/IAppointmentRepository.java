package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, String> {
}
