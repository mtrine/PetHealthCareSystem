package com.group07.PetHealthCare.controller;
import com.group07.PetHealthCare.dto.request.PetCreationRequest;
import com.group07.PetHealthCare.dto.request.ServiceRequest;
import com.group07.PetHealthCare.dto.response.ReviewsResponse;
import com.group07.PetHealthCare.dto.response.ServicesResponse;
import com.group07.PetHealthCare.service.ServicesService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class ServicesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicesService servicesService;

    private ObjectMapper objectMapper;
    private ServiceRequest serviceRequest;
    private ServicesResponse servicesResponse;
    private List <ServicesResponse> serviceResponseList;

    @BeforeEach
    void initData() {
        objectMapper = new ObjectMapper();

        serviceRequest = new ServiceRequest();
        serviceRequest.setName("Cắt lông");
        serviceRequest.setUnitPrice(BigDecimal.valueOf(50000));

        servicesResponse = ServicesResponse.builder()
                .id("eee0fc6c-374a-470d-b7d5-adace2e5bdd9")
                .unitPrice(BigDecimal.valueOf(50000))
                .name("Cắt lông")
                .build();

        serviceResponseList = Collections.singletonList(servicesResponse);
    }

    @Test
    public void createServiceTest() throws Exception {
        when(servicesService.createService(any(ServiceRequest.class))).thenReturn(servicesResponse);
        mockMvc.perform(post("/v1/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getAuthToken())
                        .content(objectMapper.writeValueAsString(serviceRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(servicesResponse.getId()))
                .andExpect(jsonPath("$.result.name").value(servicesResponse.getName()))
                .andExpect(jsonPath("$.result.unitPrice").value(servicesResponse.getUnitPrice()));

    }


    @Test
    public void getAllServicesTest() throws Exception {
        when(servicesService.getAllServices()).thenReturn(serviceResponseList);
        mockMvc.perform(get("/v1/services")
                    .header("Authorization", "Bearer " + getAuthToken())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].id").value("eee0fc6c-374a-470d-b7d5-adace2e5bdd9"))
                .andExpect(jsonPath("$.result[0].name").value("Cắt lông"))
                .andExpect(jsonPath("$.result[0].unitPrice").value(50000));

    }

    private String getAuthToken() throws Exception {
        String username = "admin@group07.com";
        String password = "admin123";

        String response = mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return new ObjectMapper().readTree(response).get("result").get("token").asText();
    }
}