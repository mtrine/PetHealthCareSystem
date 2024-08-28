package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.group07.PetHealthCare.dto.request.VisitScheduleRequest;
import com.group07.PetHealthCare.dto.response.VisitScheduleResponse;
import com.group07.PetHealthCare.service.VisitScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class VisitScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VisitScheduleService visitScheduleService;

    private VisitScheduleRequest visitScheduleRequest;
    private VisitScheduleResponse visitScheduleResponse;
    private ObjectMapper objectMapper;

    @BeforeEach
    void initData() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String visitScheduleId = "vsc-123456";
        visitScheduleRequest = new VisitScheduleRequest();
        visitScheduleRequest.setVeterinarianId("a63c23e3-751c-49d2-974e-f21fa19aad22");
        visitScheduleRequest.setHospitalizationId("62120858-f98f-4977-b520-a65ad511d61e");
        visitScheduleRequest.setVisitDate(LocalDate.of(2024, 8, 24));
        visitScheduleRequest.setSessionId(1);

        visitScheduleResponse = VisitScheduleResponse.builder()
                .visitScheduleId(visitScheduleId)
                .visitDate(LocalDate.of(2024, 8, 24))
                .build();
    }

    @Test
    void createVisitSchedule() throws Exception {
        Mockito.when(visitScheduleService.createVisitSchedule(any(VisitScheduleRequest.class)))
                .thenReturn(visitScheduleResponse);

        String requestJson = objectMapper.writeValueAsString(visitScheduleRequest);

        mockMvc.perform(post("/v1/visitschedules")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.visitScheduleId").value("vsc-123456"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.visitDate").value("2024-08-24"));
    }

    private String getAuthToken() throws Exception {
        String username = "staff@gmail.com";
        String password = "staffpass";

        String response = mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("result").get("token").asText();
    }
}
