package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Cage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICageRepository extends JpaRepository<Cage, Integer> {
}
