package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group07.PetHealthCare.dto.request.IntrospectRequest;
import com.group07.PetHealthCare.dto.request.RefreshRequest;
import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.dto.response.AuthResponse;
import com.group07.PetHealthCare.dto.response.IntrospectRespone;
import com.group07.PetHealthCare.dto.response.UserResponse;
import com.group07.PetHealthCare.service.AuthService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    private ObjectMapper objectMapper;
    private UserRequest userRequest;
    private UserResponse userResponse;
    private AuthResponse authResponse;
    private IntrospectRequest introspectRequest;
    private IntrospectRespone introspectRespone;
    private RefreshRequest refreshRequest;


    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();


        userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setEmail("user@example.com");
        userRequest.setPhoneNumber("1234567890");
        userRequest.setAddress("123 Main St");
        userRequest.setSex(true);
        userRequest.setPassword("password123");
        userRequest.setRole("CUSTOMER");

        userResponse = new UserResponse();
        userResponse.setId("10a6a291-5bd4-4fea-ab04-ef4ecaa83086");
        userResponse.setName("John Doe");
        userResponse.setEmail("user@example.com");
        userResponse.setPhoneNumber("1234567890");
        userResponse.setAddress("123 Main St");
        userResponse.setSex(true);
        userResponse.setPassword("securepassword");
        userResponse.setRole("CUSTOMER");

        authResponse = new AuthResponse();
        authResponse.setToken("token");
        authResponse.setUserResponse(userResponse);

        introspectRequest = new IntrospectRequest();
        introspectRequest.setToken("token");

        introspectRespone = new IntrospectRespone();
        introspectRespone.setValid(true);

        refreshRequest = new RefreshRequest();
        refreshRequest.setToken("old-token");
        authResponse.setToken("new-jwt-token");


    }

    @Test
    void registerUser_shouldReturnUserResponse() throws Exception {
        // Arrange
        when(authService.register(any(UserRequest.class))).thenReturn(userResponse);

        // Act & Assert
        mockMvc.perform(post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(userResponse.getId()))
                .andExpect(jsonPath("$.result.name").value(userResponse.getName()))
                .andExpect(jsonPath("$.result.email").value(userResponse.getEmail()))
                .andExpect(jsonPath("$.result.role").value(userResponse.getRole()))
                .andExpect(jsonPath("$.result.phoneNumber").value(userResponse.getPhoneNumber()))
                .andExpect(jsonPath("$.result.address").value(userResponse.getAddress()));
    }

    @Test
    void login() throws Exception {
        // Mock the service response
        when(authService.login(any(UserRequest.class))).thenReturn(authResponse);

        // Create login request JSON
        String requestJson = "{\"email\":\"user@example.com\",\"password\":\"password123\"}";

        // Perform the POST request and validate the response
        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.token").value(authResponse.getToken()))
                .andExpect(jsonPath("$.result.userResponse.id").value(userResponse.getId()))
                .andExpect(jsonPath("$.result.userResponse.name").value("John Doe"))
                .andExpect(jsonPath("$.result.userResponse.email").value("user@example.com"))
                .andExpect(jsonPath("$.result.userResponse.phoneNumber").value("1234567890"))
                .andExpect(jsonPath("$.result.userResponse.address").value("123 Main St"))
                .andExpect(jsonPath("$.result.userResponse.sex").value(true))
                .andExpect(jsonPath("$.result.userResponse.password").value("securepassword"))
                .andExpect(jsonPath("$.result.userResponse.role").value("CUSTOMER"));
    }

    @Test
    void introspect() throws Exception {
        //GIVEN
        String requestJson = objectMapper.writeValueAsString(introspectRequest);

        //WHEN, THEN
        when(authService.introspect(any(IntrospectRequest.class))).thenReturn(introspectRespone);

        mockMvc.perform(post("/v1/auth/introspect")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.valid").value(true));
    }

    @Test
    void refreshToken_Success() throws Exception {
        String requestJson = objectMapper.writeValueAsString(refreshRequest);

        when(authService.refreshToken(any(RefreshRequest.class))).thenReturn(authResponse);

        mockMvc.perform(post("/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.token").value("new-jwt-token"));
    }


    @Test
    void registerUser_whenEmail_invalid() throws Exception {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("invalid-email"); // Đặt email không hợp lệ
        userRequest.setPassword("ValidPassword123");

        // Act & Assert
        mockMvc.perform(post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email should be valid")); // Kiểm tra thông báo lỗi
    }

    @Test
    void registerUser_whenPass_invalid() throws Exception {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("email@gmail.com"); // Đặt email không hợp lệ
        userRequest.setPassword("123");

        // Act & Assert
        mockMvc.perform(post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Password should be valid")); // Kiểm tra thông báo lỗi
    }

    @Test
    void registerUser_whenName_invalid() throws Exception {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("email@gmail.com"); // Đặt email không hợp lệ
        userRequest.setPassword("123456");
        userRequest.setName("T");
        // Act & Assert
        mockMvc.perform(post("/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Name must be more than 2 character")); // Kiểm tra thông báo lỗi
    }

    @Test
    void loginUser_whenEmail_invalid() throws Exception {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("invalid-email"); // Đặt email không hợp lệ
        userRequest.setPassword("ValidPassword123");

        // Act & Assert
        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email should be valid")); // Kiểm tra thông báo lỗi
    }

    @Test
    void loginUser_whenPass_invalid() throws Exception {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("email@gmail.com"); // Đặt email không hợp lệ
        userRequest.setPassword("123");

        // Act & Assert
        mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Password should be valid")); // Kiểm tra thông báo lỗi
    }
}
