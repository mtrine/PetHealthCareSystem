package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, String> {
    Optional<Appointment> findByVeterinarianIdAndAppointmentDateAndSessionId(String veterinarianId, LocalDate appointmentDate, int sessionId);
    List<Appointment> findByVeterinarianId(String veterinarianId);
    List<Appointment> findByVeterinarianIdAndAppointmentDate(String veterinarianId, LocalDate appointmentDate);
    List<Appointment> findByPetId(String petId);
}
