package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, String> {
    Optional<Veterinarian> findByEmail(String email);
}
