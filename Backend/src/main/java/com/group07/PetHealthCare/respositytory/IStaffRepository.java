package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStaffRepository extends JpaRepository<Staff, String> {
    Optional<Staff> findByEmail(String email);
}
