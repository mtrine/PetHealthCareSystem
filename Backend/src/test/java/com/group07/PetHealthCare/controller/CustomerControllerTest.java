package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group07.PetHealthCare.controllers.CustomerController;
import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.dto.response.CustomerResponse;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

    private CustomerResponse customerResponse1;
    private CustomerResponse customerResponse2;
    private UserRequest customerRequest;

    @BeforeEach
    public void initData() {
        MockitoAnnotations.openMocks(this);
        customerRequest = new UserRequest();
        customerRequest.setName("Customer");
        customerRequest.setEmail("customer@gmail.com");
        customerRequest.setPassword("customerpass");
        customerRequest.setPhoneNumber("0969036238");
        customerRequest.setAddress("Binh Chanh");
        customerRequest.setSex(true);
        customerRequest.setRole("CUSTOMER");

        customerResponse1 = CustomerResponse.builder()
                .id("36cc9494-2e25-4527-b5c1-a5080cbdb614")
                .name("Customer")
                .address("Binh Chanh")
                .email("customer@gmail.com")
                .password("customerpass")
                .role("CUSTOMER")
                .phoneNumber("0969036238")
                .sex(true)
                .build();

        customerResponse2 = CustomerResponse.builder()
                .id("a912fcf6-f7e0-4a63-b5f8-56098d08c72c")
                .name("Nguyen Ngoc Quynh Nhu")
                .address("Binh Chanh")
                .email("nhuquynh6453@gmail.com")
                .password("quynhnhu")
                .role("CUSTOMER")
                .phoneNumber("0969036238")
                .sex(true)
                .build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        List<CustomerResponse> customers = Arrays.asList(customerResponse1, customerResponse2);
        when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].id").value("36cc9494-2e25-4527-b5c1-a5080cbdb614"))
                .andExpect(jsonPath("$.result[0].name").value("Customer"))
                .andExpect(jsonPath("$.result[0].email").value("customer@gmail.com"))
                .andExpect(jsonPath("$.result[0].address").value("Binh Chanh"))
                .andExpect(jsonPath("$.result[0].role").value("CUSTOMER"))
                .andExpect(jsonPath("$.result[0].phoneNumber").value("0969036238"))
                .andExpect(jsonPath("$.result[0].password").value("customerpass"));
    }

    @Test
    public void getCustomerById() throws Exception {
        when(customerService.getCustomerById(customerResponse1.getId())).thenReturn(customerResponse1);

        mockMvc.perform(get("/v1/customers/"+customerResponse1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + getAuthToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value("36cc9494-2e25-4527-b5c1-a5080cbdb614"))
                .andExpect(jsonPath("$.result.name").value("Customer"))
                .andExpect(jsonPath("$.result.email").value("customer@gmail.com"))
                .andExpect(jsonPath("$.result.address").value("Binh Chanh"))
                .andExpect(jsonPath("$.result.role").value("CUSTOMER"))
                .andExpect(jsonPath("$.result.phoneNumber").value("0969036238"))
                .andExpect(jsonPath("$.result.password").value("customerpass"));
    }



    @Test
    public void getCustomerById_CustomerNotFound() throws Exception {
        String invalidUserId = "invalid-id";
        when(customerService.getCustomerById(invalidUserId))
                .thenThrow(new AppException(ErrorCode.NOT_FOUND));

        mockMvc.perform(get("/v1/customers/" + invalidUserId)
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"message\":\"Not found\"}"));
    }

    private String getAuthToken() throws Exception {
        String username = "admin@group07.com";
        String password = "admin123";

        String response = mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("result").get("token").asText();
    }
}
