package com.group07.PetHealthCare.respositytory;

import com.group07.PetHealthCare.config.ReviewsId;
import com.group07.PetHealthCare.pojo.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReviewsRepository extends JpaRepository<Reviews, ReviewsId> {
}
