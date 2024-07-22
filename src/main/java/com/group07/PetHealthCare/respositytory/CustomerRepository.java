package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phone);

    Optional<Customer> findCustomerByEmail(String email);
}
