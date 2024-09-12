package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.dto.request.VaccineRequest;
import com.group07.PetHealthCare.dto.response.UserResponse;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    private ObjectMapper objectMapper;
    private List<UserResponse> userResponseList;
    private UserResponse userResponse1;
    private UserResponse userResponse2;
    private UserRequest userRequest;



    @BeforeEach
    void initData(){

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String userId = "10a6a291-5bd4-4fea-ab04-ef4ecaa83086";
        userRequest = new UserRequest();
        userRequest.setName("Jane Doe");
        userRequest.setEmail("jane.doe@example.com");
        userRequest.setPhoneNumber("0987654321");
        userRequest.setAddress("456 Another St");
        userRequest.setSex(false);
        userRequest.setPassword("newpassword123");
        userRequest.setRole("ADMIN");

        userResponse1=UserResponse.builder()
                .id("10a6a291-5bd4-4fea-ab04-ef4ecaa83086")
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .phoneNumber("0987654321")
                .address("456 Another St")
                .sex(false)
                .password("hashednewpassword")
                .role("ADMIN")
                .build();

        userResponse2=UserResponse.builder()
                .id("123")
                .name("User")
                .email("user@gmail.com")
                .phoneNumber("0123123123")
                .address("456 Another St")
                .sex(false)
                .password("hashednewpassword")
                .role("ADMIN")
                .build();

        userResponseList = Arrays.asList(userResponse1, userResponse2);

    }

    
    @Test
    @WithMockUser("ADMIN")
    void deleteUser() throws Exception {

        String userId = "dcad4277-0d82-4a5e-a3fe-8dee26dc3462";
        Mockito.doNothing().when(userService).deleteUser(anyString());

        mockMVC.perform(delete("/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    @WithMockUser("ADMIN")
    void getAllUsers() throws Exception {
        Mockito.when(userService.getAllUser()).thenReturn(userResponseList);

        mockMVC.perform(get("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].name").value("Jane Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].email").value("jane.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].phoneNumber").value("0987654321"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].address").value("456 Another St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].sex").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].role").value("ADMIN"));
    }



    @Test
    @WithMockUser("ADMIN")
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
    @WithMockUser("ADMIN")
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

        UserResponse userResponse1 = new UserResponse();
        userResponse1.setId(userId);
        userResponse1.setName("Jane Doe");
        userResponse1.setEmail("jane.doe@example.com");
        userResponse1.setPhoneNumber("0987654321");
        userResponse1.setAddress("456 Another St");
        userResponse1.setSex(false);
        userResponse1.setPassword("hashednewpassword");
        userResponse1.setRole("ADMIN");

        Mockito.when(userService.updateMyInfo(any(UserRequest.class))).thenReturn(userResponse1);

        String requestJson = new ObjectMapper().writeValueAsString(userRequest);

        mockMVC.perform(MockMvcRequestBuilders.patch("/v1/users/my-info", userId)
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
    @WithMockUser("ADMIN")
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

    @Test
    @WithMockUser("ADMIN")
    void updateInfoUser_UserNotFound() throws Exception {
        Mockito.when(userService.updateMyInfo(any(UserRequest.class)))
                .thenThrow(new AppException(ErrorCode.NOT_FOUND));


        String requestJson = objectMapper.writeValueAsString(userRequest);

        mockMVC.perform(patch("/v1/users/my-info","invalid-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not found"));
    }


    @Test
    @WithMockUser("ADMIN")
    void deleteUser_UserNotFound() throws Exception {
        Mockito.doThrow(new AppException(ErrorCode.NOT_FOUND))
                .when(userService).deleteUser(anyString());


        String requestJson = objectMapper.writeValueAsString(userRequest);

        mockMVC.perform(delete("/v1/users/{userId}","invalid-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not found"));
    }

    @Test
    @WithMockUser("ADMIN")
    void getMyInfo_UserNotFound() throws Exception {
        Mockito.when(userService.getMyInfo())
                .thenThrow(new AppException(ErrorCode.NOT_FOUND));


        String requestJson = objectMapper.writeValueAsString(userRequest);

        mockMVC.perform(get("/v1/users/my-info","invalid-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not found"));
    }

    @Test
    @WithMockUser("ADMIN")
    void updateMyInfo_UserNotFound() throws Exception {
        Mockito.when(userService.updateMyInfo(any(UserRequest.class)))
                .thenThrow(new AppException(ErrorCode.NOT_FOUND));


        String requestJson = objectMapper.writeValueAsString(userRequest);

        mockMVC.perform(patch("/v1/users/my-info","invalid-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not found"));
    }
}
