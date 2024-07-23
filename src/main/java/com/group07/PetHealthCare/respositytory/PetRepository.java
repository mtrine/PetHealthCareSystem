package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet,String> {
}
