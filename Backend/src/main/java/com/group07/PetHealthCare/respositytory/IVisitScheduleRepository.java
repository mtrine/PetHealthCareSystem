package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.VisitSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVisitScheduleRepository extends JpaRepository<VisitSchedule, String> {
}
