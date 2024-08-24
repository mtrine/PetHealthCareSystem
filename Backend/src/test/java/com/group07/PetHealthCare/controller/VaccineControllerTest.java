package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.group07.PetHealthCare.dto.request.VaccineRequest;
import com.group07.PetHealthCare.dto.response.VaccineResponse;
import com.group07.PetHealthCare.service.VaccineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VaccineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VaccineService vaccineService;

    private ObjectMapper objectMapper;
    private VaccineRequest vaccineRequest;
    private VaccineResponse vaccineResponse;
    private List<VaccineResponse> vaccineResponseList;

    @BeforeEach
    void initData() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        vaccineRequest = new VaccineRequest();
        vaccineRequest.setName("Covid");

        vaccineResponse = VaccineResponse.builder()
                .id("df797e5a-1707-41da-946f-201becfd1552")
                .name("Covid")
                .build();

        vaccineResponseList = Collections.singletonList(vaccineResponse);
    }

    @Test
    void GetVaccine() throws Exception {
        Mockito.when(vaccineService.getVaccine(anyString())).thenReturn(vaccineResponse);

        mockMvc.perform(get("/v1/vaccines/{id}", vaccineResponse.getId())
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value("df797e5a-1707-41da-946f-201becfd1552"))
                .andExpect(jsonPath("$.result.name").value("Covid"));
    }

    @Test
    void CreateVaccine() throws Exception {
        Mockito.when(vaccineService.createVaccine(any(VaccineRequest.class))).thenReturn(vaccineResponse);
        mockMvc.perform(post("/v1/vaccines")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vaccineRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value("df797e5a-1707-41da-946f-201becfd1552"))
                .andExpect(jsonPath("$.result.name").value("Covid"));
    }

    @Test
    void UpdateVaccine() throws Exception {
        Mockito.when(vaccineService.updateVaccine(anyString(), any(VaccineRequest.class))).thenReturn(vaccineResponse);

        mockMvc.perform(patch("/v1/vaccines/{id}","df797e5a-1707-41da-946f-201becfd1552")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vaccineRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value("df797e5a-1707-41da-946f-201becfd1552"))
                .andExpect(jsonPath("$.result.name").value("Covid"));
    }

    @Test
    void testDeleteVaccine() throws Exception {
        Mockito.doNothing().when(vaccineService).deleteVaccine(anyString());
        mockMvc.perform(delete("/v1/vaccines/{id}", vaccineResponse.getId())
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllVaccines() throws Exception {
        Mockito.when(vaccineService.getVaccines()).thenReturn(vaccineResponseList);

        mockMvc.perform(get("/v1/vaccines")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result[0].id").value("df797e5a-1707-41da-946f-201becfd1552"))
                .andExpect(jsonPath("$.result[0].name").value("Covid"));
    }

    private String getAuthToken() throws Exception {
        String username = "admin@group07.com";
        String password = "admin123";

        String response = mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return new ObjectMapper().readTree(response).get("result").get("token").asText();
    }
}
