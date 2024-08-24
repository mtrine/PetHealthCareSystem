package com.group07.PetHealthCare.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.group07.PetHealthCare.dto.request.VaccinePetRequest;
import com.group07.PetHealthCare.dto.response.VaccinePetResponse;
import com.group07.PetHealthCare.service.VaccinePetService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class VaccinePetControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    private VaccinePetService vaccinePetService;
    private VaccinePetRequest vaccinePetRequest;
    private VaccinePetResponse vaccinePetResponse;

    @BeforeEach
    void initData() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        vaccinePetRequest = new VaccinePetRequest();
        vaccinePetRequest.setPetId("4543d3af-e527-48a8-ab25-c85873f78d95");
        vaccinePetRequest.setVaccineId("df797e5a-1707-41da-946f-201becfd1552");
        vaccinePetRequest.setStingDate(LocalDate.of(2024, 8, 18));
        vaccinePetRequest.setReStingDate(LocalDate.of(2025, 8, 18));

        vaccinePetResponse = VaccinePetResponse.builder()
                .petId("4543d3af-e527-48a8-ab25-c85873f78d95")
                .vaccineId("df797e5a-1707-41da-946f-201becfd1552")
                .stingDate(LocalDate.of(2024,8,18))
                .reStingDate(LocalDate.of(2025,8,18))
                .build();

    }
}
