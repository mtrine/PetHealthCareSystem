package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.enumData.Role;
import com.group07.PetHealthCare.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    User findByRole(Role role);

}
