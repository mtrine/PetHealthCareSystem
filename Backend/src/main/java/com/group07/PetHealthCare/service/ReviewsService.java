package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.config.ReviewsId;
import com.group07.PetHealthCare.dto.request.ReviewsRequest;
import com.group07.PetHealthCare.dto.response.ReviewsResponse;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.mapper.IReviewsMapper;
import com.group07.PetHealthCare.pojo.Appointment;
import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.pojo.Reviews;
import com.group07.PetHealthCare.pojo.User;
import com.group07.PetHealthCare.respositytory.IAppointmentRepository;
import com.group07.PetHealthCare.respositytory.ICustomerRepository;
import com.group07.PetHealthCare.respositytory.IReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewsService {
    @Autowired
    IReviewsRepository reviewsRepository;

    @Autowired
    IReviewsMapper reviewsMapper;

    @Autowired
    IAppointmentRepository appointmentRepository;

    @Autowired
    ICustomerRepository customerRepository;

    public ReviewsResponse createReview(ReviewsRequest reviewsRequest) {
        // Fetching the required entities
        Appointment appointment = appointmentRepository.findById(reviewsRequest.getAppointmentId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        Customer customer = customerRepository.findById(reviewsRequest.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        // Create composite key
        ReviewsId reviewsId = new ReviewsId(reviewsRequest.getUserId(), reviewsRequest.getAppointmentId());

        // Create Reviews instance
        Reviews reviews = new Reviews();
        reviews.setId(reviewsId); // Setting the composite key
        reviews.setAppointment(appointment);
        reviews.setCustomer(customer);
        reviews.setGrades(reviewsRequest.getGrades());
        reviews.setComment(reviewsRequest.getComments());
        reviews.setReviewDate(LocalDate.now());

        // Save and return response
        return reviewsMapper.toReviewsResponse(reviewsRepository.save(reviews));
    }

    public List<ReviewsResponse> getAllReviews() {
        List<Reviews> reviews = reviewsRepository.findAll();
        return reviewsMapper.toReviewsResponse(reviews);
    }
}

