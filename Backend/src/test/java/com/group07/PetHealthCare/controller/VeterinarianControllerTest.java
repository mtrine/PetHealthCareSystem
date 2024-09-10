package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.SessionsRequest;
import com.group07.PetHealthCare.dto.request.VeterinarianRequest;
import com.group07.PetHealthCare.dto.request.VisitScheduleRequest;
import com.group07.PetHealthCare.dto.response.SessionResponse;
import com.group07.PetHealthCare.dto.response.VaccineResponse;
import com.group07.PetHealthCare.dto.response.VeterinarianResponse;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.pojo.Session;
import com.group07.PetHealthCare.service.VeterinarianService;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class VeterinarianControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    private VeterinarianService veterinarianService;
    private VeterinarianRequest veterinarianRequest;
    private VeterinarianResponse veterinarianResponse;
    private List <VeterinarianResponse> veterinarianResponseList;
    private SessionsRequest sessionsRequest;
    private SessionResponse sessionResponse;
    private List <SessionResponse> sessionResponseList;

    @BeforeEach
    void initData(){
        veterinarianRequest = new VeterinarianRequest();
        veterinarianRequest.setFullTime(true);
        veterinarianRequest.setName("Veterinarian");
        veterinarianRequest.setAddress("Binh Chanh");
        veterinarianRequest.setPhoneNumber("0969036238");
        veterinarianRequest.setEmail("veterinarian@gmail.com");
        veterinarianRequest.setSex(true);
        veterinarianRequest.setRole("VETERINARIAN");
        veterinarianRequest.setPassword("veterinarianpass");

        veterinarianResponse = VeterinarianResponse.builder()
                .id("a63c23e3-751c-49d2-974e-f21fa19aad22")
                .name("Veterinarian")
                .address("Binh Chanh")
                .phoneNumber("0969036238")
                .email("veterinarian@gmail.com")
                .role("VETERINARIAN")
                .password("veterinarianpass")
                .sex(true)
                .build();

        veterinarianResponseList = List.of(veterinarianResponse);

        sessionsRequest = new SessionsRequest();
        sessionsRequest.setStartTime(LocalTime.of(8, 0));
        sessionsRequest.setEndTime(LocalTime.of(10, 0));

        sessionResponse = SessionResponse.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .startTime(LocalTime.of(8, 0,0))
                .endTime(LocalTime.of(10, 0,0))
                .build();

        sessionResponseList = List.of(sessionResponse);
    }

    @Test
    void testGetAllVeterinarian() throws Exception {
        when(veterinarianService.getAllVeterinarian()).thenReturn(veterinarianResponseList);

        mockMvc.perform(get("/v1/veterinarians")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result[0].id").value("a63c23e3-751c-49d2-974e-f21fa19aad22"))
                .andExpect(jsonPath("$.result[0].name").value("Veterinarian"))
                .andExpect(jsonPath("$.result[0].address").value("Binh Chanh"))
                .andExpect(jsonPath("$.result[0].phoneNumber").value("0969036238"))
                .andExpect(jsonPath("$.result[0].role").value("VETERINARIAN"))
                .andExpect(jsonPath("$.result[0].password").value("veterinarianpass"))
                .andExpect(jsonPath("$.result[0].email").value("veterinarian@gmail.com"));
    }

    @Test
    void getVeterinarianById() throws Exception {
        when(veterinarianService.getVeterinarianById(anyString())).thenReturn(veterinarianResponse);

        mockMvc.perform(get("/v1/veterinarians/{id}","a63c23e3-751c-49d2-974e-f21fa19aad22")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value("a63c23e3-751c-49d2-974e-f21fa19aad22"))
                .andExpect(jsonPath("$.result.name").value("Veterinarian"))
                .andExpect(jsonPath("$.result.address").value("Binh Chanh"))
                .andExpect(jsonPath("$.result.phoneNumber").value("0969036238"))
                .andExpect(jsonPath("$.result.email").value("veterinarian@gmail.com"))
                .andExpect(jsonPath("$.result.role").value("VETERINARIAN"))
                .andExpect(jsonPath("$.result.password").value("veterinarianpass"));
    }

    @Test
    void getAvailableSession() throws Exception {
        // Mock the service to return the prepared sessionResponseList
        when(veterinarianService.getAvailableSessionsForVeterinarian(anyString(), any(LocalDate.class)))
                .thenReturn(sessionResponseList);

        // Perform the GET request and assert the expected response
        mockMvc.perform(get("/v1/veterinarians/{veterinarianID}/available-sessions", "a63c23e3-751c-49d2-974e-f21fa19aad22")
                        .param("date", "2024-08-24") // Set the date parameter
                        .header("Authorization", "Bearer " + getAuthToken()) // Include the authorization header
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Expect JSON response
                .andExpect(jsonPath("$.result[0].id").value("123e4567-e89b-12d3-a456-426614174000")) // Validate session ID
                .andExpect(jsonPath("$.result[0].startTime").value("08:00:00")) // Validate start time
                .andExpect(jsonPath("$.result[0].endTime").value("10:00:00")); // Validate end time
    }

    @Test
    void getAvailableVeterinariansForSessionAndDate() throws Exception {
        // Mock the service to return the prepared veterinarianResponseList
        when(veterinarianService.getAvailableVeterinariansForSessionAndDate(anyInt(), any(LocalDate.class)))
                .thenReturn(veterinarianResponseList);

        // Perform the GET request and assert the expected response
        mockMvc.perform(get("/v1/veterinarians/{sessionId}/get-available-veterinarian-session", 1)
                        .param("date", "2024-08-24") // Set the date parameter
                        .header("Authorization", "Bearer " + getAuthToken()) // Include the authorization header
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Expect HTTP 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Expect JSON response
                .andExpect(jsonPath("$.result[0].id").value("a63c23e3-751c-49d2-974e-f21fa19aad22")) // Validate veterinarian ID
                .andExpect(jsonPath("$.result[0].name").value("Veterinarian")) // Validate name
                .andExpect(jsonPath("$.result[0].address").value("Binh Chanh")) // Validate address
                .andExpect(jsonPath("$.result[0].phoneNumber").value("0969036238")) // Validate phone number
                .andExpect(jsonPath("$.result[0].role").value("VETERINARIAN")) // Validate role
                .andExpect(jsonPath("$.result[0].email").value("veterinarian@gmail.com")); // Validate email
    }

    @Test
    void GetVeterinarianById_NotFound() throws Exception {
        when(veterinarianService.getVeterinarianById(anyString())).thenThrow(new AppException(ErrorCode.NOT_FOUND));

        mockMvc.perform(get("/v1/veterinarians/{id}", "invalid-id")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not found"));
    }

    @Test
    void GetAvailableSessionsForVeterinarian_NotFound() throws Exception {
        when(veterinarianService.getAvailableSessionsForVeterinarian(anyString(), any(LocalDate.class)))
                .thenThrow(new AppException(ErrorCode.NOT_FOUND));

        mockMvc.perform(get("/v1/veterinarians/{veterinarianID}/available-sessions", "a63c23e3-751c-49d2-974e-f21fa19aad22")
                        .param("date", "2024-08-24")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()) // Expect HTTP 404 NOT FOUND
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Not found"));
    }

    @Test
    void GetMyInfo_NotFound() throws Exception {
        when(veterinarianService.getMyInfo())
                .thenThrow(new AppException(ErrorCode.NOT_FOUND));

        mockMvc.perform(get("/v1/veterinarians/my-info")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Not found"));
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
