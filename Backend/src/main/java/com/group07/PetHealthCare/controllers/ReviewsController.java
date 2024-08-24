package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.ReviewsRequest;
import com.group07.PetHealthCare.dto.response.ReviewsResponse;
import com.group07.PetHealthCare.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/reviews")
public class ReviewsController {
    @Autowired
    private ReviewsService reviewsService;

    @PostMapping
    public ApiResponse<ReviewsResponse> createReview(@RequestBody ReviewsRequest reviewsRequest) {
        ApiResponse<ReviewsResponse> apiResponse = new ApiResponse<>();
        ReviewsResponse reviewsResponse = reviewsService.createReview(reviewsRequest);
        apiResponse.setResult(reviewsResponse);
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<ReviewsResponse>> getAllReviews() {
        ApiResponse<List<ReviewsResponse>> apiResponse = new ApiResponse<>();
        List<ReviewsResponse> reviewsResponse = reviewsService.getAllReviews();
        apiResponse.setResult(reviewsResponse);
        return apiResponse;
    }

    @PostMapping("/my-reviews")
    public ApiResponse<ReviewsResponse> createMyReview(@RequestBody ReviewsRequest reviewsRequest) {
        ApiResponse<ReviewsResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(reviewsService.createMyReview(reviewsRequest));
        return apiResponse;
    }
}
