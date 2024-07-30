package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Hospitalization;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IHospitalizationRepository extends JpaRepository<Hospitalization, String>{
}
