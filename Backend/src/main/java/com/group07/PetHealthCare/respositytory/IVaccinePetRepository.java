package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.VaccinePet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVaccinePetRepository extends JpaRepository<VaccinePet,String> {
}
