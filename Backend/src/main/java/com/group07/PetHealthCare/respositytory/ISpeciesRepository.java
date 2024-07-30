package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISpeciesRepository extends JpaRepository<Species, String> {
}
