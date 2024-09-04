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
                .id("36cc9494-2e25-4527-b5c1-a5080cbdb614")
                .name("Customer")
                .email("customer@gmail.com")
                .phoneNumber("096903628")
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
                .id("7a6f764f-fd24-4aa9-8b48-d0a878e927b7")
                .status("Scheduled")
                .sessionResponse(sessionResponse)
                .appointmentDate(LocalDate.of(2024, 8, 15))
                .deposit(100.0)
                .veterinarianName("Dr. Smith")
                .servicesResponsesList(servicesResponseSet)
                .pet(petResponse)
                .description("Mệt mỏi và chảy máu")
                .build();

        appointmentResponseList = Collections.singletonList(appointmentResponse);

        appointmentRequest = new AppointmentRequest();
        appointmentRequest.setStatus("Scheduled");
        appointmentRequest.setAppointmentDate(LocalDate.of(2024, 8, 15));
        appointmentRequest.setDeposit(100.0);
        appointmentRequest.setVeterinarianId("2c741f51-0a22-4e2f-8022-c8093fe46964");
        appointmentRequest.setSessionId(1);
        appointmentRequest.setDescription("Thú bị đau bụng");
    }

    @Test
    void AddAppointmentBySession() throws Exception {
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
    void GetAppointmentByVeterinarianId() throws Exception {
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
    void GetAllAppointments() throws Exception {
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

    @Test
    void getAppointmentByCustomerId() throws Exception {
        Mockito.when(appointmentService.getAppointmentByCustomerId("36cc9494-2e25-4527-b5c1-a5080cbdb614"))
                .thenReturn(appointmentResponseList);

        mockMvc.perform(get("/v1/appointments/{customerId}/customer", "36cc9494-2e25-4527-b5c1-a5080cbdb614")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.result[0].id").value("7a6f764f-fd24-4aa9-8b48-d0a878e927b7"))
                .andExpect(jsonPath("$.result[0].status").value("Scheduled"))
                .andExpect(jsonPath("$.result[0].description").value("Mệt mỏi và chảy máu"))
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
    void getMyAppointmentForCustomer() throws Exception {
        // Mock the service layer to return a list of AppointmentResponse objects
        Mockito.when(appointmentService.getMyAppointmentForCustomer())
                .thenReturn(appointmentResponseList);

        // Perform a GET request to the endpoint and verify the response
        mockMvc.perform(get("/v1/appointments/my-appointment-for-customer")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result[0].id").value(appointmentResponse.getId()))
                .andExpect(jsonPath("$.result[0].status").value(appointmentResponse.getStatus()))
                .andExpect(jsonPath("$.result[0].description").value(appointmentResponse.getDescription()))
                .andExpect(jsonPath("$.result[0].appointmentDate").value("2024-08-15"))
                .andExpect(jsonPath("$.result[0].sessionResponse.id").value("1"))
                .andExpect(jsonPath("$.result[0].veterinarianName").value("Dr. Smith"));

    }

    @Test
    void getAppointmentByPetId() throws Exception {
        Mockito.when(appointmentService.getAppointmentByPetId("f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9"))
                .thenReturn(appointmentResponseList);

        mockMvc.perform(get("/v1/appointments/{petId}/pets", "f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result[0].id").value("7a6f764f-fd24-4aa9-8b48-d0a878e927b7"))
                .andExpect(jsonPath("$.result[0].status").value("Scheduled"))
                .andExpect(jsonPath("$.result[0].description").value(appointmentResponse.getDescription()))
                .andExpect(jsonPath("$.result[0].appointmentDate").value("2024-08-15"))
                .andExpect(jsonPath("$.result[0].sessionResponse.id").value("1"))
                .andExpect(jsonPath("$.result[0].veterinarianName").value("Dr. Smith"));
    }

    @Test
    void getAppointmentByAppointmentId() throws Exception {
        Mockito.when(appointmentService.getAppointmentDetail("1"))
                .thenReturn(appointmentResponse);

        mockMvc.perform(get("/v1/appointments/{appointmentId}", "1")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value("1"))
                .andExpect(jsonPath("$.result.status").value("Scheduled"))
                .andExpect(jsonPath("$.result.description").value("Thú bị đau bụng"));
    }

    @Test
    void getMyAppointmentForVeterinarian() throws Exception {
        Mockito.when(appointmentService.getMyAppointmentForVeterinarian())
                .thenReturn(appointmentResponseList);

        mockMvc.perform(get("/v1/appointments/my-appointment-for-veterinarian")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.result[0].id").value("7a6f764f-fd24-4aa9-8b48-d0a878e927b7"))
                .andExpect(jsonPath("$.result[0].status").value("Scheduled"))
                .andExpect(jsonPath("$.result[0].description").value("Mệt mỏi và chảy máu"))
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

    private String getAuthTokenForVet() throws Exception {
        String username = "veterinarian@gmail.com";
        String password = "veterinarianpass";

        String response = mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return new ObjectMapper().readTree(response).get("result").get("token").asText();
    }
}
