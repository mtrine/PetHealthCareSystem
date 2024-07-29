package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Services,String> {
}