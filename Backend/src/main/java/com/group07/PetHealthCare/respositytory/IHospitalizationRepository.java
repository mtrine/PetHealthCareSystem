package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Hospitalization;
import com.group07.PetHealthCare.pojo.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IHospitalizationRepository extends JpaRepository<Hospitalization, String>{
    List<Hospitalization> findAllByPetID(Pet pet);
}
