package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdminRepository extends JpaRepository<Admin, String> {

}
