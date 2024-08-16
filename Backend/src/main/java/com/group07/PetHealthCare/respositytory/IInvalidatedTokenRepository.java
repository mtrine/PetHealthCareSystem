package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.pojo.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
