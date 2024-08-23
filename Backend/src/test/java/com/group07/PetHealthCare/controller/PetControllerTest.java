package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group07.PetHealthCare.dto.request.PetCreationRequest;
import com.group07.PetHealthCare.dto.request.PetUpdateRequest;
import com.group07.PetHealthCare.dto.response.CustomerResponse;
import com.group07.PetHealthCare.dto.response.PetResponse;
import com.group07.PetHealthCare.dto.response.SpeciesResponse;
import com.group07.PetHealthCare.service.PetService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    private ObjectMapper objectMapper;
    private PetCreationRequest petCreationRequest;
    private PetUpdateRequest petUpdateRequest;
    private PetResponse petResponse;
    private CustomerResponse customerResponse;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        petCreationRequest = new PetCreationRequest();
        petCreationRequest.setName("Bông");
        petCreationRequest.setAge(3);
        petCreationRequest.setSpeciesID("971c1a50-e074-43d3-a8db-6e7f5bd1a9c2");
        petCreationRequest.setCustomerID("e80bac44-998c-44db-b548-2d03b12e8a25");

        petUpdateRequest = new PetUpdateRequest();
        petUpdateRequest.setName("Bông");
        petUpdateRequest.setAge(4);
        petUpdateRequest.setSpeciesID("971c1a50-e074-43d3-a8db-6e7f5bd1a9c2");

        SpeciesResponse speciesResponse = new SpeciesResponse();
        speciesResponse.setId("971c1a50-e074-43d3-a8db-6e7f5bd1a9c2");
        speciesResponse.setName("Dog");

        customerResponse = new CustomerResponse();
        customerResponse.setId("e80bac44-998c-44db-b548-2d03b12e8a25");
        customerResponse.setName("Nguyen Ngoc Quynh Nhu");
        customerResponse.setEmail("nhuquynh6453@gmail.com");
        customerResponse.setPhoneNumber(null);
        customerResponse.setRole("CUSTOMER");

        petResponse = new PetResponse();
        petResponse.setId("f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9");
        petResponse.setName("Bông");
        petResponse.setAge(3);
        petResponse.setSpeciesResponse(speciesResponse);
        petResponse.setGender(true);
        petResponse.setCustomerResponse(customerResponse);
    }

    @Test
    void CreatePet() throws Exception {
        when(petService.addPet(any(PetCreationRequest.class))).thenReturn(petResponse);

        mockMvc.perform(post("/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getAuthToken())
                        .content(objectMapper.writeValueAsString(petCreationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.message").value("Successful"))
                .andExpect(jsonPath("$.result.id").value(petResponse.getId()))
                .andExpect(jsonPath("$.result.name").value(petResponse.getName()))
                .andExpect(jsonPath("$.result.age").value(petResponse.getAge()))
                .andExpect(jsonPath("$.result.speciesResponse.id").value(petResponse.getSpeciesResponse().getId()))
                .andExpect(jsonPath("$.result.speciesResponse.name").value(petResponse.getSpeciesResponse().getName()))
                .andExpect(jsonPath("$.result.customerResponse.id").value(customerResponse.getId()))
                .andExpect(jsonPath("$.result.customerResponse.name").value(customerResponse.getName()));
    }

    @Test
    void updatePet() throws Exception {
        String requestJson = objectMapper.writeValueAsString(petUpdateRequest);

        when(petService.updatePet(any(String.class), any(PetUpdateRequest.class))).thenReturn(petResponse);

        mockMvc.perform(patch("/v1/pets/{id}", petResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getAuthToken())
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value(petResponse.getId()))
                .andExpect(jsonPath("$.result.name").value(petResponse.getName()))
                .andExpect(jsonPath("$.result.age").value(petResponse.getAge()))
                .andExpect(jsonPath("$.result.speciesResponse.id").value(petResponse.getSpeciesResponse().getId()))
                .andExpect(jsonPath("$.result.speciesResponse.name").value(petResponse.getSpeciesResponse().getName()))
                .andExpect(jsonPath("$.result.customerResponse.id").value(customerResponse.getId()))
                .andExpect(jsonPath("$.result.customerResponse.name").value(customerResponse.getName()));
    }

    @Test
    void getPetById() throws Exception {
        when(petService.getPetById(petResponse.getId())).thenReturn(petResponse);

        mockMvc.perform(get("/v1/pets/{id}", petResponse.getId())
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value(petResponse.getId()))
                .andExpect(jsonPath("$.result.name").value(petResponse.getName()))
                .andExpect(jsonPath("$.result.age").value(petResponse.getAge()))
                .andExpect(jsonPath("$.result.speciesResponse.id").value(petResponse.getSpeciesResponse().getId()))
                .andExpect(jsonPath("$.result.speciesResponse.name").value(petResponse.getSpeciesResponse().getName()))
                .andExpect(jsonPath("$.result.customerResponse.id").value(customerResponse.getId()))
                .andExpect(jsonPath("$.result.customerResponse.name").value(customerResponse.getName()));
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

        // Print the response to debug
        System.out.println("Login Response: " + response);

        // Adjust parsing based on the actual structure
        return new ObjectMapper().readTree(response).get("result").get("token").asText();
    }


}
