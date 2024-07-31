package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, String> {
    Optional<Appointment> findByVeterinarianIdAndAppointmentDateAndSessionId(String veterinarianId, Date appointmentDate, String sessionId);
    List<Appointment> findByVeterinarianId(String veterinarianId);
}
