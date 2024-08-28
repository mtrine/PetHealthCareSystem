package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.group07.PetHealthCare.dto.request.ReviewsRequest;
import com.group07.PetHealthCare.dto.response.AppointmentResponse;
import com.group07.PetHealthCare.dto.response.CustomerResponse;
import com.group07.PetHealthCare.dto.response.ReviewsResponse;
import com.group07.PetHealthCare.dto.response.SessionResponse;
import com.group07.PetHealthCare.service.ReviewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewsService reviewsService;

    private ObjectMapper objectMapper;
    private ReviewsResponse reviewsResponse;
    private List<ReviewsResponse> reviewsResponseList;
    private CustomerResponse customerResponse;
    private AppointmentResponse appointmentResponse;
    private SessionResponse sessionResponse;
    private ReviewsRequest reviewsRequest;

    @BeforeEach
    void initData() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        customerResponse = CustomerResponse.builder()
                .id("e80bac44-998c-44db-b548-2d03b12e8a25")
                .name("Nguyen Ngoc Quynh Nhu")
                .email("nhuquynh6453@gmail.com")
                .phoneNumber(null)
                .role("CUSTOMER")
                .build();

        sessionResponse = SessionResponse.builder()
                .id("1")
                .startTime(LocalTime.of(8, 0, 0))
                .endTime(LocalTime.of(10, 0, 0))
                .build();

        appointmentResponse = AppointmentResponse.builder()
                .id("00c66505-9386-460f-b999-1e7b1e32c0a7")
                .status("Scheduled")
                .sessionResponse(sessionResponse)
                .appointmentDate(LocalDate.of(2024, 8, 15))
//                .deposit(BigDecimal.valueOf(100.00))
                .veterinarianName("Dr. Smith")
//                .serviceName(Collections.singleton("Vaccination"))
                .build();

        reviewsResponse = ReviewsResponse.builder()
                .customerresponse(customerResponse)
                .appointmentresponse(appointmentResponse)
                .grades(5)
                .comment("Excellent service!")
                .reviewDate(LocalDate.now())
                .build();

        reviewsRequest = new ReviewsRequest();
        reviewsRequest.setReviewDate(LocalDate.of(2024, 8, 15));
        reviewsRequest.setGrades(5);
        reviewsRequest.setComments("Excellent service!");
        reviewsRequest.setUserId("e80bac44-998c-44db-b548-2d03b12e8a25");
        reviewsRequest.setAppointmentId("00c66505-9386-460f-b999-1e7b1e32c0a7");

        reviewsResponseList = Arrays.asList(reviewsResponse);
    }

    @Test
    public void getAllReviewsTest() throws Exception {

        List<ReviewsResponse> reviewsResponseList = Collections.singletonList(reviewsResponse);

        // Giả lập hành vi của service
        Mockito.when(reviewsService.getAllReviews()).thenReturn(reviewsResponseList);

        // Gọi API và kiểm tra kết quả
        mockMvc.perform(get("/v1/reviews")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].customerresponse.name").value("Nguyen Ngoc Quynh Nhu"))
                .andExpect(jsonPath("$.result[0].grades").value(5))
                .andExpect(jsonPath("$.result[0].comment").value("Excellent service!"))
                .andExpect(jsonPath("$.result[0].reviewDate").value(LocalDate.now().toString()));
    }

    @Test
    void createReview() throws Exception {
        Mockito.when(reviewsService.createReview(any(ReviewsRequest.class))).thenReturn(reviewsResponse);

        mockMvc.perform(post("/v1/reviews")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewsRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.customerresponse.name").value("Nguyen Ngoc Quynh Nhu"))
                .andExpect(jsonPath("$.result.appointmentresponse.id").value("00c66505-9386-460f-b999-1e7b1e32c0a7"))
                .andExpect(jsonPath("$.result.grades").value(5))
                .andExpect(jsonPath("$.result.comment").value("Excellent service!"))
                .andExpect(jsonPath("$.result.reviewDate").value(LocalDate.now().toString()));
    }

    private String getAuthToken() throws Exception {
        String username = "mtriS@gmail.com";
        String password = "123456";

        String response = mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return new ObjectMapper().readTree(response).get("result").get("token").asText();
    }
}
