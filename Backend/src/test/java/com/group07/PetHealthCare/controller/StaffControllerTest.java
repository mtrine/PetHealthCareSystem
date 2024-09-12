package com.group07.PetHealthCare.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.dto.response.StaffResponse;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.service.StaffService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class StaffControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    private StaffService staffService;
    private UserRequest staffRequest;
    private StaffResponse staffResponse;
    private List<StaffResponse> staffResponseList;

    @BeforeEach
    void initData(){
        objectMapper = new ObjectMapper();

        staffRequest = new UserRequest();
        staffRequest.setName("Staff");
        staffRequest.setEmail("staff@gmail.com");
        staffRequest.setPassword("staffpass");
        staffRequest.setRole("STAFF");
        staffRequest.setPhoneNumber("0969036238");
        staffRequest.setAddress("Binh Chanh");
        staffRequest.setSex(true);

        staffResponse= StaffResponse.builder()
                .id("123")
                .name("Staff")
                .email("staff@gmail.com")
                .address("Binh Chanh")
                .role("STAFF")
                .phoneNumber("0969036238")
                .password("staffpass")
                .build();

        staffResponseList = Collections.singletonList(staffResponse);
    }

    @Test
    @WithMockUser("ADMIN")
    void getAllStaff() throws Exception {
        when(staffService.getAllStaff()).thenReturn(staffResponseList);

        mockMvc.perform(get("/v1/staffs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].id").value("123"))
                .andExpect(jsonPath("$.result[0].name").value("Staff"))
                .andExpect(jsonPath("$.result[0].email").value("staff@gmail.com"))
                .andExpect(jsonPath("$.result[0].address").value("Binh Chanh"))
                .andExpect(jsonPath("$.result[0].role").value("STAFF"))
                .andExpect(jsonPath("$.result[0].phoneNumber").value("0969036238"))
                .andExpect(jsonPath("$.result[0].password").value("staffpass"));

    }

    @Test
    @WithMockUser("ADMIN")
    void getStaffById() throws Exception {
        when(staffService.getStaffById(staffResponse.getId())).thenReturn(staffResponse);

        mockMvc.perform(get("/v1/staffs/" + staffResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value("123"))
                .andExpect(jsonPath("$.result.name").value("Staff"))
                .andExpect(jsonPath("$.result.email").value("staff@gmail.com"))
                .andExpect(jsonPath("$.result.address").value("Binh Chanh"))
                .andExpect(jsonPath("$.result.role").value("STAFF"))
                .andExpect(jsonPath("$.result.phoneNumber").value("0969036238"))
                .andExpect(jsonPath("$.result.password").value("staffpass"));
    }

    @Test
    @WithMockUser("ADMIN")
    void getStaffById_StaffNotFound() throws Exception {
        Mockito.when(staffService.getStaffById(anyString()))
                .thenThrow(new AppException(ErrorCode.NOT_FOUND));

        String requestJson = objectMapper.writeValueAsString(staffRequest);

        mockMvc.perform(get("/v1/staffs/{staffId}","invalid-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not found"));
    }


}
