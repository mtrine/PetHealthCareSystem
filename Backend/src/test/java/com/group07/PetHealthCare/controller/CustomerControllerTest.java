package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group07.PetHealthCare.controllers.CustomerController;
import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.response.CustomerResponse;
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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void testGetAllCustomers() throws Exception {
        CustomerResponse customerResponse1 = CustomerResponse.builder()
                .id("fe541397-4612-42c6-86eb-1da57cd1716c")
                .name("Customer")
                .address("Binh Chanh")
                .email("customer@gmail.com")
                .password("customerpass")
                .role("CUSTOMER")
                .phoneNumber("0969036238")
                .sex(true)
                .build();

        CustomerResponse customerResponse2 = CustomerResponse.builder()
                .id("a912fcf6-f7e0-4a63-b5f8-56098d08c72c")
                .name("Nguyen Ngoc Quynh Nhu")
                .address("Binh Chanh")
                .email("nhuquynh6453@gmail.com")
                .password("quynhnhu")
                .role("CUSTOMER")
                .phoneNumber("0969036238")
                .sex(true)
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
    public void testGetCustomerById() throws Exception {
        String userId = "fe541397-4612-42c6-86eb-1da57cd1716c";

        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(userId)
                .name("Customer")
                .address("Binh Chanh")
                .email("customer@gmail.com")
                .password("customerpass")
                .role("CUSTOMER")
                .phoneNumber("0969036238")
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
