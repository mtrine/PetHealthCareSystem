package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.group07.PetHealthCare.dto.request.AppointmentRequest;
import com.group07.PetHealthCare.dto.response.*;
import com.group07.PetHealthCare.service.AppointmentService;
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
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    private ObjectMapper objectMapper;
    private AppointmentResponse appointmentResponse;
    private List<AppointmentResponse> appointmentResponseList;
    private AppointmentRequest appointmentRequest;
    private Set<ServicesResponse> servicesResponseSet;
    private ServicesResponse servicesResponse;
    private PetResponse petResponse;

    @BeforeEach
    void initData() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        SessionResponse sessionResponse = SessionResponse.builder()
                .id("1")
                .startTime(LocalTime.of(8, 0, 0))
                .endTime(LocalTime.of(10, 0, 0))
                .build();

        SpeciesResponse speciesResponse = SpeciesResponse.builder()
                .id("971c1a50-e074-43d3-a8db-6e7f5bd1a9c2")
                .name("Dog")
                .build();

        CustomerResponse customerResponse = CustomerResponse.builder()
                .id("e80bac44-998c-44db-b548-2d03b12e8a25")
                .name("Nguyen Ngoc Quynh Nhu")
                .email("nhuquynh6453@gmail.com")
                .phoneNumber(null)
                .role("CUSTOMER")
                .build();

        petResponse = PetResponse.builder()
                .id("f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9")
                .name("Bông")
                .age(3)
                .speciesResponse(speciesResponse)
                .gender(true)
                .customerResponse(customerResponse)
                .build();

        servicesResponse = ServicesResponse.builder()
                .id("123")
                .name("Tỉa lông")
                .unitPrice(BigDecimal.valueOf(500))
                .build();

        servicesResponseSet = Set.of(servicesResponse);

        appointmentResponse = AppointmentResponse.builder()
                .id("1")
                .status("Scheduled")
                .sessionResponse(sessionResponse)
                .appointmentDate(LocalDate.of(2024, 8, 15))
//                .deposit(BigDecimal.valueOf(100.00))
                .veterinarianName("Dr. Smith")
                .servicesResponsesList(servicesResponseSet)
                .pet(petResponse)
                .description("Thú bị đau bụng")
                .build();

        appointmentResponseList = Collections.singletonList(appointmentResponse);

        appointmentRequest = new AppointmentRequest();
        appointmentRequest.setStatus("Scheduled");
        appointmentRequest.setAppointmentDate(LocalDate.of(2024, 8, 15));
//        appointmentRequest.setDeposit(BigDecimal.valueOf(100.00));
        appointmentRequest.setVeterinarianId("2c741f51-0a22-4e2f-8022-c8093fe46964");
        appointmentRequest.setSessionId(1);
        appointmentRequest.setDescription("Thú bị đau bụng");
    }

    @Test
    void testAddAppointmentBySession() throws Exception {
        Mockito.when(appointmentService.addAppointmentBySession(any(AppointmentRequest.class)))
                .thenReturn(appointmentResponse);

        mockMvc.perform(post("/v1/appointments/addBySession")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.result.id").value("1"))
                .andExpect(jsonPath("$.result.status").value("Scheduled"))
                .andExpect(jsonPath("$.result.description").value("Thú bị đau bụng"))
                .andExpect(jsonPath("$.result.appointmentDate").value("2024-08-15"))
                .andExpect(jsonPath("$.result.deposit").value(100.00))
                .andExpect(jsonPath("$.result.veterinarianName").value("Dr. Smith"))

                .andExpect(jsonPath("$.result.sessionResponse.id").value("1"))
                .andExpect(jsonPath("$.result.sessionResponse.startTime").value("08:00:00"))
                .andExpect(jsonPath("$.result.sessionResponse.endTime").value("10:00:00"))

                .andExpect(jsonPath("$.result.servicesResponsesList[0].id").value("123"))
                .andExpect(jsonPath("$.result.servicesResponsesList[0].name").value("Tỉa lông"))
                .andExpect(jsonPath("$.result.servicesResponsesList[0].unitPrice").value(500))

                .andExpect(jsonPath("$.result.pet.id").value("f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9"))
                .andExpect(jsonPath("$.result.pet.name").value("Bông"))
                .andExpect(jsonPath("$.result.pet.age").value(3))
                .andExpect(jsonPath("$.result.pet.gender").value(true))
                .andExpect(jsonPath("$.result.pet.speciesResponse.id").value("971c1a50-e074-43d3-a8db-6e7f5bd1a9c2"))
                .andExpect(jsonPath("$.result.pet.speciesResponse.name").value("Dog"));
    }

    @Test
    void testGetAppointmentByVeterinarianId() throws Exception {
        Mockito.when(appointmentService.getAppointmentByVeterinarianId(anyString()))
                .thenReturn(appointmentResponseList);

        mockMvc.perform(get("/v1/appointments/{veterinarianId}/veterinarians", "2c741f51-0a22-4e2f-8022-c8093fe46964")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result[0].id").value("1"))
                .andExpect(jsonPath("$.result[0].status").value("Scheduled"))
                .andExpect(jsonPath("$.result[0].description").value("Thú bị đau bụng"))
                .andExpect(jsonPath("$.result[0].appointmentDate").value("2024-08-15"))
                .andExpect(jsonPath("$.result[0].deposit").value(100.00))
                .andExpect(jsonPath("$.result[0].veterinarianName").value("Dr. Smith"))

                .andExpect(jsonPath("$.result[0].sessionResponse.id").value("1"))
                .andExpect(jsonPath("$.result[0].sessionResponse.startTime").value("08:00:00"))
                .andExpect(jsonPath("$.result[0].sessionResponse.endTime").value("10:00:00"))

                .andExpect(jsonPath("$.result[0].servicesResponsesList[0].id").value("123"))
                .andExpect(jsonPath("$.result[0].servicesResponsesList[0].name").value("Tỉa lông"))
                .andExpect(jsonPath("$.result[0].servicesResponsesList[0].unitPrice").value(500))

                .andExpect(jsonPath("$.result[0].pet.id").value("f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9"))
                .andExpect(jsonPath("$.result[0].pet.name").value("Bông"))
                .andExpect(jsonPath("$.result[0].pet.age").value(3))
                .andExpect(jsonPath("$.result[0].pet.gender").value(true))
                .andExpect(jsonPath("$.result[0].pet.speciesResponse.id").value("971c1a50-e074-43d3-a8db-6e7f5bd1a9c2"))
                .andExpect(jsonPath("$.result[0].pet.speciesResponse.name").value("Dog"));
    }

    @Test
    void testGetAllAppointments() throws Exception {
        Mockito.when(appointmentService.getAllAppointments()).thenReturn(appointmentResponseList);

        mockMvc.perform(get("/v1/appointments")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.result[0].id").value("1"))
                .andExpect(jsonPath("$.result[0].status").value("Scheduled"))
                .andExpect(jsonPath("$.result[0].description").value("Thú bị đau bụng"))
                .andExpect(jsonPath("$.result[0].appointmentDate").value("2024-08-15"))
                .andExpect(jsonPath("$.result[0].deposit").value(100.00))
                .andExpect(jsonPath("$.result[0].veterinarianName").value("Dr. Smith"))

                .andExpect(jsonPath("$.result[0].sessionResponse.id").value("1"))
                .andExpect(jsonPath("$.result[0].sessionResponse.startTime").value("08:00:00"))
                .andExpect(jsonPath("$.result[0].sessionResponse.endTime").value("10:00:00"))

                .andExpect(jsonPath("$.result[0].servicesResponsesList[0].id").value("123"))
                .andExpect(jsonPath("$.result[0].servicesResponsesList[0].name").value("Tỉa lông"))
                .andExpect(jsonPath("$.result[0].servicesResponsesList[0].unitPrice").value(500))

                // Kiểm tra petResponse như một nhóm biến
                .andExpect(jsonPath("$.result[0].pet.id").value("f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9"))
                .andExpect(jsonPath("$.result[0].pet.name").value("Bông"))
                .andExpect(jsonPath("$.result[0].pet.age").value(3))
                .andExpect(jsonPath("$.result[0].pet.gender").value(true))
                .andExpect(jsonPath("$.result[0].pet.speciesResponse.id").value("971c1a50-e074-43d3-a8db-6e7f5bd1a9c2"))
                .andExpect(jsonPath("$.result[0].pet.speciesResponse.name").value("Dog"));
    }


    private String getAuthToken() throws Exception {
        String username = "customer@gmail.com";
        String password = "customerpass";

        String response = mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return new ObjectMapper().readTree(response).get("result").get("token").asText();
    }
}
