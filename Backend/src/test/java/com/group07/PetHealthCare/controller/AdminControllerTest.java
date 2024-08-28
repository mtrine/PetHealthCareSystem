package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group07.PetHealthCare.dto.response.AdminResponse;
import com.group07.PetHealthCare.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    private ObjectMapper objectMapper;
    private AdminResponse adminResponse;
    private List<AdminResponse> adminResponseList;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        adminResponse = AdminResponse.builder()
                .id("1a2b3c4d")
                .name("Dr. Admin")
                .email("admin@example.com")
                .phoneNumber("0987654321")
                .address("789 Admin St")
                .role("ADMIN")
                .build();

        adminResponseList = Arrays.asList(adminResponse);
    }

    @Test
    void getAllAdmin() throws Exception {
        // Mock the service response
        Mockito.when(adminService.getAllAdmins()).thenReturn(adminResponseList);

        // Perform the GET request and validate the response
        mockMvc.perform(get("/v1/admins")
                        .header("Authorization", "Bearer " + getAuthToken()) // Add Authorization header
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result[0].name").value("Dr. Admin"))
                .andExpect(jsonPath("$.result[0].email").value("admin@example.com"))
                .andExpect(jsonPath("$.result[0].phoneNumber").value("0987654321"))
                .andExpect(jsonPath("$.result[0].address").value("789 Admin St"))
                .andExpect(jsonPath("$.result[0].role").value("ADMIN"));
    }

    @Test
    void getAdmin() throws Exception {
        // Mock the service response
        Mockito.when(adminService.getAdminById(anyString())).thenReturn(adminResponse);

        // Perform the GET request and validate the response
        mockMvc.perform(get("/v1/admins/{userId}", "1a2b3c4d")
                        .header("Authorization", "Bearer " + getAuthToken()) // Add Authorization header
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.name").value("Dr. Admin"))
                .andExpect(jsonPath("$.result.email").value("admin@example.com"))
                .andExpect(jsonPath("$.result.phoneNumber").value("0987654321"))
                .andExpect(jsonPath("$.result.address").value("789 Admin St"))
                .andExpect(jsonPath("$.result.role").value("ADMIN"));
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

        // Print the response to debug
        System.out.println("Login Response: " + response);

        // Adjust parsing based on the actual structure
        return new ObjectMapper().readTree(response).get("result").get("token").asText();
    }


}
