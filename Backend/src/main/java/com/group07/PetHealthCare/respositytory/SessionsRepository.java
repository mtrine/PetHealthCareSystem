package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionsRepository extends JpaRepository<Session, String> {

}
