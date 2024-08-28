package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.dto.response.UserResponse;
import com.group07.PetHealthCare.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMVC;

    @MockBean
    private UserService userService;

    private UserRequest request;
    private UserResponse userResponse;



    @BeforeTestClass
    void initData(){
        String userId = "10a6a291-5bd4-4fea-ab04-ef4ecaa83086";
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Jane Doe");
        userRequest.setEmail("jane.doe@example.com");
        userRequest.setPhoneNumber("0987654321");
        userRequest.setAddress("456 Another St");
        userRequest.setSex(false);
        userRequest.setPassword("newpassword123");
        userRequest.setRole("ADMIN");

        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setName("Jane Doe");
        userResponse.setEmail("jane.doe@example.com");
        userResponse.setPhoneNumber("0987654321");
        userResponse.setAddress("456 Another St");
        userResponse.setSex(false);
        userResponse.setPassword("hashednewpassword");
        userResponse.setRole("ADMIN");

    }

    
    @Test
    void deleteUser() throws Exception {

        String userId = "dcad4277-0d82-4a5e-a3fe-8dee26dc3462";
        Mockito.doNothing().when(userService).deleteUser(anyString());

        mockMVC.perform(delete("/v1/users/{id}", userId)
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    private String getAuthToken() throws Exception {

        String username = "admin@group07.com";
        String password = "admin123";

        String response = mockMVC.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return new ObjectMapper().readTree(response).get("result").get("token").asText();
    }

    @Test
    void updateUser() throws Exception {
        String userId = "dcad4277-0d82-4a5e-a3fe-8dee26dc3462";
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Jane Doe");
        userRequest.setEmail("jane.doe@example.com");
        userRequest.setPhoneNumber("0987654321");
        userRequest.setAddress("456 Another St");
        userRequest.setSex(false);
        userRequest.setPassword("newpassword123");
        userRequest.setRole("ADMIN");

        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setName("Jane Doe");
        userResponse.setEmail("jane.doe@example.com");
        userResponse.setPhoneNumber("0987654321");
        userResponse.setAddress("456 Another St");
        userResponse.setSex(false);
        userResponse.setPassword("hashednewpassword");
        userResponse.setRole("ADMIN");

        Mockito.when(userService.updateInforUser(Mockito.anyString(), Mockito.any(UserRequest.class))).thenReturn(userResponse);

        String requestJson = new ObjectMapper().writeValueAsString(userRequest);

        mockMVC.perform(MockMvcRequestBuilders.patch("/v1/users/{userId}", userId)
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.name").value("Jane Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.email").value("jane.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.phoneNumber").value("0987654321"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.address").value("456 Another St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.sex").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.role").value("ADMIN"));
    }

    @Test
    void updateUserInfo() throws Exception {
        String userId = "dcad4277-0d82-4a5e-a3fe-8dee26dc3462";
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Jane Doe");
        userRequest.setEmail("jane.doe@example.com");
        userRequest.setPhoneNumber("0987654321");
        userRequest.setAddress("456 Another St");
        userRequest.setSex(false);
        userRequest.setPassword("newpassword123");
        userRequest.setRole("ADMIN");

        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setName("Jane Doe");
        userResponse.setEmail("jane.doe@example.com");
        userResponse.setPhoneNumber("0987654321");
        userResponse.setAddress("456 Another St");
        userResponse.setSex(false);
        userResponse.setPassword("hashednewpassword");
        userResponse.setRole("ADMIN");

        Mockito.when(userService.updateMyInfo(any(UserRequest.class))).thenReturn(userResponse);

        String requestJson = new ObjectMapper().writeValueAsString(userRequest);

        mockMVC.perform(MockMvcRequestBuilders.patch("/v1/users/update-my-info", userId)
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.name").value("Jane Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.email").value("jane.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.phoneNumber").value("0987654321"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.address").value("456 Another St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.sex").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.role").value("ADMIN"));
    }

    @Test
    void getUserInfo() throws Exception {
        String userId = "10a6a291-5bd4-4fea-ab04-ef4ecaa83086"; // The user ID to test
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setName("Jane Doe");
        userResponse.setEmail("jane.doe@example.com");
        userResponse.setPhoneNumber("0987654321");
        userResponse.setAddress("456 Another St");
        userResponse.setSex(false);
        userResponse.setPassword("hashednewpassword");
        userResponse.setRole("ADMIN");

        Mockito.when(userService.getMyInfo()).thenReturn(userResponse);

        mockMVC.perform(MockMvcRequestBuilders.get("/v1/users/my-info")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.id").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.name").value("Jane Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.email").value("jane.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.phoneNumber").value("0987654321"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.address").value("456 Another St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.sex").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.role").value("ADMIN"));
    }


}
