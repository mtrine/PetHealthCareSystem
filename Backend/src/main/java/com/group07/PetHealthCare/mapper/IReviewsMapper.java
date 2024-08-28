package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.ReviewsResponse;
import com.group07.PetHealthCare.pojo.Reviews;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IAppointmentMapper.class})
public interface IReviewsMapper {

    @Mapping(source = "reviews.customer", target = "customerresponse")
    @Mapping(source = "reviews.appointment", target = "appointmentresponse")
    ReviewsResponse toReviewsResponse(Reviews reviews);

    @Mapping(source = "reviewsResponse.customerresponse", target = "customer")
    @Mapping(source = "reviewsResponse.appointmentresponse", target = "appointment")
    Reviews toReviews(ReviewsResponse reviewsResponse);

    List<ReviewsResponse> toReviewsResponse(List<Reviews> reviewsList);
}
