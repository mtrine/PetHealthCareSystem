package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group07.PetHealthCare.dto.request.ServiceRequest;
import com.group07.PetHealthCare.dto.request.SpeciesRequest;
import com.group07.PetHealthCare.dto.response.SpeciesResponse;
import com.group07.PetHealthCare.service.SpeciesService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
public class SpeciesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SpeciesService speciesService;

    private SpeciesRequest speciesRequest;
    private SpeciesResponse speciesResponse;
    private List<SpeciesResponse> speciesResponseList;

    @BeforeEach
    void initData() {
        speciesRequest = new SpeciesRequest();
        speciesRequest.setName("Dog");

        speciesResponse = SpeciesResponse.builder()
                .id("1")
                .name("Dog")
                .build();
        speciesResponseList = Collections.singletonList(speciesResponse);
    }

    @Test
    @WithMockUser("ADMIN")
    void getAllSpecies() throws Exception {
        // Mocking the service layer to return the speciesResponseList
        when(speciesService.getAllSpecies()).thenReturn(speciesResponseList);

        // Performing the GET request and verifying the response
        mockMvc.perform(get("/v1/species")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].id").value("1"))
                .andExpect(jsonPath("$.result[0].name").value("Dog"));
    }


    @Test
    @WithMockUser("ADMIN")
    public void createService() throws Exception {
        when(speciesService.addSpecies(any(SpeciesRequest.class))).thenReturn(speciesResponse);
        mockMvc.perform(post("/v1/species")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(speciesRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value("1"))
                .andExpect(jsonPath("$.result.name").value("Dog"));
    }
}
