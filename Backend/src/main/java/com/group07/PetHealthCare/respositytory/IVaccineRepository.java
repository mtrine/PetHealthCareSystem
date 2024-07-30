package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVaccineRepository extends JpaRepository<Vaccine, String> {
}
