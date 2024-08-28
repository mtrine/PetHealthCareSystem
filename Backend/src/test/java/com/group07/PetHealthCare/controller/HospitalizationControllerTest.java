package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.HospitalizationRequest;
import com.group07.PetHealthCare.dto.response.HospitalizationResponse;
import com.group07.PetHealthCare.service.HospitalizationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class HospitalizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HospitalizationService hospitalizationService;

    private ObjectMapper objectMapper;
    private HospitalizationRequest hospitalizationRequest;
    private HospitalizationResponse hospitalizationResponse;

    @BeforeEach
    void initData() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        hospitalizationRequest = new HospitalizationRequest();
        hospitalizationRequest.setPetID("pet123");
        hospitalizationRequest.setCageNumber(1);
        hospitalizationRequest.setStartDate(LocalDate.of(2024, 8, 18));
        hospitalizationRequest.setEndDate(LocalDate.of(2024, 8, 20));

        hospitalizationResponse = new HospitalizationResponse();
        hospitalizationResponse.setId("hospitalization123");
        hospitalizationResponse.setReasonForHospitalization("Đau bụng");
        hospitalizationResponse.setHealthCondition("Yếu");
        hospitalizationResponse.setStartDate(LocalDate.of(2024, 8, 18));
        hospitalizationResponse.setEndDate(LocalDate.of(2024, 8, 20));
    }

    @Test
    void createHospitalization() throws Exception {
        // GIVEN
        String requestJson = objectMapper.writeValueAsString(hospitalizationRequest);

        // WHEN, THEN
        when(hospitalizationService.createHospitalization(any(HospitalizationRequest.class))).thenReturn(hospitalizationResponse);

        mockMvc.perform(post("/v1/hospitalizations")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value("hospitalization123"))
                .andExpect(jsonPath("$.result.reasonForHospitalization").value("Đau bụng"))
                .andExpect(jsonPath("$.result.healthCondition").value("Yếu"))
                .andExpect(jsonPath("$.result.startDate").value("2024-08-18"))
                .andExpect(jsonPath("$.result.endDate").value("2024-08-20"));
    }



    @Test
    void updateHospitalization() throws Exception {
        // GIVEN
        String requestJson = objectMapper.writeValueAsString(hospitalizationRequest);

        // WHEN, THEN
        when(hospitalizationService.updateHospitalization(Mockito.anyString(), any(HospitalizationRequest.class))).thenReturn(hospitalizationResponse);

        mockMvc.perform(patch("/v1/hospitalizations/hospitalization123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getAuthToken())
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value("hospitalization123"));
    }

    @Test
    void getAllHospitalizations() throws Exception {
        // GIVEN
        List<HospitalizationResponse> hospitalizationList = new ArrayList<>();
        hospitalizationList.add(hospitalizationResponse);

        // WHEN, THEN
        when(hospitalizationService.getAllHospitalization()).thenReturn(hospitalizationList);

        mockMvc.perform(get("/v1/hospitalizations")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.size()").value(1));
    }

    @Test
    void deleteHospitalization() throws Exception {
        // WHEN, THEN
        mockMvc.perform(delete("/v1/hospitalizations/hospitalization123")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("Deleted Successfully"));
    }

    @Test
    void getHospitalization() throws Exception {
        // WHEN, THEN
        when(hospitalizationService.getHospitalizationById(Mockito.anyString())).thenReturn(hospitalizationResponse);

        mockMvc.perform(get("/v1/hospitalizations/hospitalization123")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value("hospitalization123"));
    }

    @Test
    void getHospitalizationByPet() throws Exception {
        // GIVEN
        List<HospitalizationResponse> hospitalizationList = new ArrayList<>();
        hospitalizationList.add(hospitalizationResponse);

        // WHEN, THEN
        when(hospitalizationService.getHospitalizationByPetId(Mockito.anyString())).thenReturn(hospitalizationList);

        mockMvc.perform(get("/v1/hospitalizations?petId=pet123")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.size()").value(1));
    }

    private String getAuthToken() throws Exception {
        String username = "mtriS2@gmail.com";
        String password = "123456";

        String response = mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Print the response to debug
        System.out.println("Login Response: " + response);

        // Adjust parsing based on the actual structure
        return new ObjectMapper().readTree(response).get("result").get("token").asText();
    }
}
