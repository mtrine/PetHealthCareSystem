package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.VisitSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface IVisitScheduleRepository extends JpaRepository<VisitSchedule, String> {
    Optional<VisitSchedule> findByVeterinarianIdAndVisitDateAndSessionId(String veterinarianId, LocalDate visitDate, String sessionId);
}
