package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Veterinarianschedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface IVeterinarianscheduleRepository extends JpaRepository<Veterinarianschedule,String> {
}
