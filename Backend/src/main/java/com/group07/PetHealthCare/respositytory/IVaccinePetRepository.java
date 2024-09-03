package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.config.VaccinePetId;
import com.group07.PetHealthCare.pojo.Pet;
import com.group07.PetHealthCare.pojo.Vaccine;
import com.group07.PetHealthCare.pojo.VaccinePet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVaccinePetRepository extends JpaRepository<VaccinePet, VaccinePetId> {
    List<VaccinePet> findAllByPet(Pet pet);
    VaccinePet findByPetAndVaccine(Pet pet, Vaccine vaccine);
}
