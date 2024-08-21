package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group07.PetHealthCare.controllers.CustomerController;
import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.response.CustomerResponse;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@AutoConfigureMockMvc
@SpringBootTest
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void initData() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void GetAllCustomers() throws Exception {
        CustomerResponse customerResponse1 = CustomerResponse.builder()
                .id("10a6a291-5bd4-4fea-ab04-ef4ecaa83086")
                .name("John Doe")
                .address("123 Main St")
                .email("user@example.com")
                .password("securepassword")
                .role("CUSTOMER")
                .sex(true)
                .build();

        CustomerResponse customerResponse2 = CustomerResponse.builder()
                .id("e80bac44-998c-44db-b548-2d03b12e8a25")
                .name("Nguyen Ngoc Quynh Nhu")
                .address("Binh Chanh")
                .email("nhuquynh6453@gmail.com")
                .password("quynhnhu")
                .role("CUSTOMER")
                .sex(false)
                .build();

        ApiResponse<List<CustomerResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(Arrays.asList(customerResponse1, customerResponse2));

        when(customerService.getAllCustomers()).thenReturn(apiResponse.getResult());

        mockMvc.perform(get("/v1/customers")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(apiResponse)));
    }

    @Test
    public void getCustomerById() throws Exception {
        String userId = "10a6a291-5bd4-4fea-ab04-ef4ecaa83086";

        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(userId)
                .name("John Doe")
                .address("123 Main St")
                .email("user@example.com")
                .password("securepassword")
                .role("CUSTOMER")
                .sex(true)
                .build();

        ApiResponse<CustomerResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(customerResponse);

        when(customerService.getCustomerById(userId)).thenReturn(apiResponse.getResult());

        mockMvc.perform(get("/v1/customers/" + userId)
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(apiResponse)));
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

        // Print the response to debug
        System.out.println("Login Response: " + response);

        // Adjust parsing based on the actual structure
        return new ObjectMapper().readTree(response).get("result").get("token").asText();
    }

}
