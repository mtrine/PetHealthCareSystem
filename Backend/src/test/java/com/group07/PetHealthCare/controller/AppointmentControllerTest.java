package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group07.PetHealthCare.dto.request.AppointmentRequest;
import com.group07.PetHealthCare.dto.response.AppointmentResponse;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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

    @BeforeEach
    void initData() {
        objectMapper = new ObjectMapper();

        appointmentResponse = AppointmentResponse.builder()
                .id("1")
                .status("Scheduled")
                .description("Routine check-up")
                .appointmentDate(LocalDate.of(2024, 8, 15))
                .deposit(BigDecimal.valueOf(100.00))
                .veterinarianName("Dr. Smith")
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(11, 0))
                .serviceName(Set.of("Vaccination"))
                .build();

        appointmentResponseList = Arrays.asList(appointmentResponse);
    }

    private String getAuthToken() throws Exception {
        String username = "user@example.com";
        String password = "securepassword";

        String response = mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return new ObjectMapper().readTree(response).get("result").get("token").asText();
    }

    @Test
    void addAppointmentBySession() throws Exception {
        AppointmentRequest request = new AppointmentRequest();

        AppointmentResponse response = appointmentResponse;

        Mockito.when(appointmentService.addAppointmentBySession(any(AppointmentRequest.class)))
                .thenReturn(response);

        String authToken = getAuthToken();

        mockMvc.perform(post("/v1/appointments/addBySession")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value("1"))
                .andExpect(jsonPath("$.result.status").value("Scheduled"))
                .andExpect(jsonPath("$.result.description").value("Routine check-up"))
                .andExpect(jsonPath("$.result.appointmentDate").value("2024-08-15"))
                .andExpect(jsonPath("$.result.deposit").value(100.00))
                .andExpect(jsonPath("$.result.veterinarianName").value("Dr. Smith"))
                .andExpect(jsonPath("$.result.startTime").value("10:00:00"))
                .andExpect(jsonPath("$.result.endTime").value("11:00:00"))
                .andExpect(jsonPath("$.result.serviceName[0]").value("Vaccination"));
    }

    @Test
    void addAppointmentByVeterinarian() throws Exception {
        AppointmentRequest request = new AppointmentRequest();
        AppointmentResponse response = appointmentResponse;

        Mockito.when(appointmentService.addAppointmentByVeterinarian(any(AppointmentRequest.class)))
                .thenReturn(response);

        String authToken = getAuthToken();

        mockMvc.perform(post("/v1/appointments/addByVeterinarian")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value("1"))
                .andExpect(jsonPath("$.result.status").value("Scheduled"))
                .andExpect(jsonPath("$.result.description").value("Routine check-up"))
                .andExpect(jsonPath("$.result.appointmentDate").value("2024-08-15"))
                .andExpect(jsonPath("$.result.deposit").value(100.00))
                .andExpect(jsonPath("$.result.veterinarianName").value("Dr. Smith"))
                .andExpect(jsonPath("$.result.startTime").value("10:00:00"))
                .andExpect(jsonPath("$.result.endTime").value("11:00:00"))
                .andExpect(jsonPath("$.result.serviceName[0]").value("Vaccination"));
    }

    @Test
    void getAppointmentByVeterinarianId() throws Exception {
        Mockito.when(appointmentService.getAppointmentByVeterinarianId(anyString()))
                .thenReturn(appointmentResponseList);

        String authToken = getAuthToken();

        mockMvc.perform(get("/v1/appointments/{veterinarianId}", "vet1")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result[0].id").value("1"))
                .andExpect(jsonPath("$.result[0].status").value("Scheduled"))
                .andExpect(jsonPath("$.result[0].description").value("Routine check-up"))
                .andExpect(jsonPath("$.result[0].appointmentDate").value("2024-08-15"))
                .andExpect(jsonPath("$.result[0].deposit").value(100.00))
                .andExpect(jsonPath("$.result[0].veterinarianName").value("Dr. Smith"))
                .andExpect(jsonPath("$.result[0].startTime").value("10:00:00"))
                .andExpect(jsonPath("$.result[0].endTime").value("11:00:00"))
                .andExpect(jsonPath("$.result[0].serviceName[0]").value("Vaccination"));
    }

    @Test
    void getAllAppointments() throws Exception {
        Mockito.when(appointmentService.getAllAppointments()).thenReturn(appointmentResponseList);

        String authToken = getAuthToken();

        mockMvc.perform(get("/v1/appointments")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result[0].id").value("1"))
                .andExpect(jsonPath("$.result[0].status").value("Scheduled"))
                .andExpect(jsonPath("$.result[0].description").value("Routine check-up"))
                .andExpect(jsonPath("$.result[0].appointmentDate").value("2024-08-15"))
                .andExpect(jsonPath("$.result[0].deposit").value(100.00))
                .andExpect(jsonPath("$.result[0].veterinarianName").value("Dr. Smith"))
                .andExpect(jsonPath("$.result[0].startTime").value("10:00:00"))
                .andExpect(jsonPath("$.result[0].endTime").value("11:00:00"))
                .andExpect(jsonPath("$.result[0].serviceName[0]").value("Vaccination"));
    }

    @Test
    void changeInforAppointment() throws Exception {
        AppointmentRequest request = new AppointmentRequest();
        // Fill in the request data as necessary

        AppointmentResponse response = appointmentResponse;

        Mockito.when(appointmentService.changeInforAppointment(anyString(), any(AppointmentRequest.class)))
                .thenReturn(response);

        String authToken = getAuthToken();

        mockMvc.perform(patch("/v1/appointments/{appointmentId}", "1")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value("1"))
                .andExpect(jsonPath("$.result.status").value("Scheduled"))
                .andExpect(jsonPath("$.result.description").value("Routine check-up"))
                .andExpect(jsonPath("$.result.appointmentDate").value("2024-08-15"))
                .andExpect(jsonPath("$.result.deposit").value(100.00))
                .andExpect(jsonPath("$.result.veterinarianName").value("Dr. Smith"))
                .andExpect(jsonPath("$.result.startTime").value("10:00:00"))
                .andExpect(jsonPath("$.result.endTime").value("11:00:00"))
                .andExpect(jsonPath("$.result.serviceName[0]").value("Vaccination"));
    }
}
