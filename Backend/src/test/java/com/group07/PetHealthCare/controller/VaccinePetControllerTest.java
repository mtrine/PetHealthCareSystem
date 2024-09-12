package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.group07.PetHealthCare.dto.request.VaccinePetRequest;
import com.group07.PetHealthCare.dto.request.VisitScheduleRequest;
import com.group07.PetHealthCare.dto.response.*;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.service.VaccinePetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@AutoConfigureMockMvc
public class VaccinePetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockBean
    private VaccinePetService vaccinePetService;

    private VaccinePetRequest vaccinePetRequest;
    private VaccinePetResponse vaccinePetResponse;
    private PetResponse petResponse;
    private CustomerResponse customerResponse;
    private SpeciesResponse speciesResponse;
    private VaccineResponse vaccineResponse;

    @BeforeEach
    void initData() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        speciesResponse = new SpeciesResponse();
        speciesResponse.setId("971c1a50-e074-43d3-a8db-6e7f5bd1a9c2");
        speciesResponse.setName("Dog");

        customerResponse = new CustomerResponse();
        customerResponse.setId("e80bac44-998c-44db-b548-2d03b12e8a25");
        customerResponse.setName("Nguyen Ngoc Quynh Nhu");
        customerResponse.setEmail("nhuquynh6453@gmail.com");

        petResponse = PetResponse.builder()
                .id("4543d3af-e527-48a8-ab25-c85873f78d95")
                .name("Bông")
                .age(3)
                .customerResponse(customerResponse)
                .speciesResponse(speciesResponse)
                .gender(true)
                .build();

        vaccineResponse = VaccineResponse.builder()
                .id("df797e5a-1707-41da-946f-201becfd1552")
                .name("Covid")
                .build();

        vaccinePetResponse = VaccinePetResponse.builder()
                .petResponse(petResponse)
                .vaccineResponse(vaccineResponse)
                .stingDate(LocalDate.of(2024, 8, 18))
                .reStingDate(LocalDate.of(2025, 8, 18))
                .build();

        vaccinePetRequest = new VaccinePetRequest();
        vaccinePetRequest.setPetId("4543d3af-e527-48a8-ab25-c85873f78d95");
        vaccinePetRequest.setVaccineId("df797e5a-1707-41da-946f-201becfd1552");
        vaccinePetRequest.setStingDate(LocalDate.of(2024, 8, 18));
        vaccinePetRequest.setReStingDate(LocalDate.of(2025, 8, 18));
    }

    @Test
    @WithMockUser("STAFF")
    void getVaccinePetByPetId() throws Exception {
        Mockito.when(vaccinePetService.getVaccinePetByPetId(anyString()))
                .thenReturn(Collections.singletonList(vaccinePetResponse));

        mockMvc.perform(get("/v1/vaccines-pet/{petId}/pets", "4543d3af-e527-48a8-ab25-c85873f78d95")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].petResponse.name").value("Bông"))
                .andExpect(jsonPath("$.result[0].vaccineResponse.name").value("Covid"))
                .andExpect(jsonPath("$.result[0].stingDate").value("2024-08-18"))
                .andExpect(jsonPath("$.result[0].reStingDate").value("2025-08-18"));
    }

    @Test
    @WithMockUser("STAFF")
    void addVaccineToPet() throws Exception {
        Mockito.when(vaccinePetService.addVaccineToPet(any(VaccinePetRequest.class)))
                .thenReturn(vaccinePetResponse);

        String requestJson = objectMapper.writeValueAsString(vaccinePetRequest);

        mockMvc.perform(post("/v1/vaccines-pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.petResponse.name").value("Bông"))
                .andExpect(jsonPath("$.result.vaccineResponse.name").value("Covid"))
                .andExpect(jsonPath("$.result.stingDate").value("2024-08-18"))
                .andExpect(jsonPath("$.result.reStingDate").value("2025-08-18"));
    }

    @Test
    @WithMockUser("STAFF")
    void addVaccineToPet_PetNotFound() throws Exception {
        Mockito.when(vaccinePetService.addVaccineToPet(any(VaccinePetRequest.class)))
                .thenThrow(new RuntimeException("Pet not found"));
        String requestJson = objectMapper.writeValueAsString(vaccinePetRequest);

        mockMvc.perform(post("/v1/vaccines-pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Pet not found"));
    }

    @Test
    @WithMockUser("STAFF")
    void addVaccineToPet_VaccineNotFound() throws Exception {
        Mockito.when(vaccinePetService.addVaccineToPet(any(VaccinePetRequest.class)))
                .thenThrow(new RuntimeException("Vaccine not found"));
        String requestJson = objectMapper.writeValueAsString(vaccinePetRequest);

        mockMvc.perform(post("/v1/vaccines-pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Vaccine not found"));
    }

    @Test
    @WithMockUser("STAFF")
    void getVaccinePetByPetId_PetNotFound() throws Exception {
        Mockito.when(vaccinePetService.getVaccinePetByPetId(anyString()))
                .thenThrow(new RuntimeException("Pet not found"));
        String requestJson = objectMapper.writeValueAsString(vaccinePetRequest);

        mockMvc.perform(get("/v1/vaccines-pet/{petId}/pets","invalid-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Pet not found"));
    }

}
