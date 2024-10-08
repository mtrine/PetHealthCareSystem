package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group07.PetHealthCare.dto.request.CageRequest;
import com.group07.PetHealthCare.dto.response.CageResponse;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.service.CagesService;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CagesService cagesService;

    private ObjectMapper objectMapper;
    private CageResponse cageResponse;
    private List<CageResponse> cageResponseList;

    @BeforeEach
    void initData() {
        objectMapper = new ObjectMapper();

        cageResponse = CageResponse.builder()
                .cageNumber(1)
                .status(true)
                .unitPrice(BigDecimal.valueOf(50.00))
                .build();

        cageResponseList = Arrays.asList(cageResponse);
    }

    @Test
    @WithMockUser("STAFF")
    void getAllCages() throws Exception {
        Mockito.when(cagesService.getAllCages()).thenReturn(cageResponseList);

        mockMvc.perform(get("/v1/cages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result[0].cageNumber").value(1))
                .andExpect(jsonPath("$.result[0].status").value(true))
                .andExpect(jsonPath("$.result[0].unitPrice").value(50.00));
    }
    @Test
    @WithMockUser("STAFF")
    void createCage() throws Exception {
        CageRequest cageRequest = new CageRequest();
        cageRequest.setStatus(true);
        cageRequest.setUnitPrice(BigDecimal.valueOf(200000));

        CageResponse cageResponse = CageResponse.builder()
                .cageNumber(1)
                .status(true)
                .unitPrice(BigDecimal.valueOf(200000))
                .build();

        Mockito.when(cagesService.addCage(any(CageRequest.class))).thenReturn(cageResponse);

        mockMvc.perform(post("/v1/cages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cageRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.cageNumber").value(1))
                .andExpect(jsonPath("$.result.status").value(true))
                .andExpect(jsonPath("$.result.unitPrice").value(200000));
    }

    @Test
    @WithMockUser("STAFF")
    void getCageById() throws Exception {
        CageRequest cageRequest = new CageRequest();
        cageRequest.setStatus(true);
        cageRequest.setUnitPrice(BigDecimal.valueOf(200000));

        CageResponse cageResponse = CageResponse.builder()
                .cageNumber(1)
                .status(true)
                .unitPrice(BigDecimal.valueOf(200000))
                .build();


        Mockito.when(cagesService.getCageById(anyInt())).thenReturn(cageResponse);

        mockMvc.perform(get("/v1/cages/{id}", cageResponse.getCageNumber())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cageRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.cageNumber").value(1))
                .andExpect(jsonPath("$.result.status").value(true))
                .andExpect(jsonPath("$.result.unitPrice").value(200000));

    }

    @Test
    @WithMockUser("ADMIN")
    void getCageById_NotFound () throws Exception {
        CageRequest cageRequest = new CageRequest();
        cageRequest.setStatus(true);
        cageRequest.setUnitPrice(BigDecimal.valueOf(200000));

        CageResponse cageResponse = CageResponse.builder()
                .cageNumber(1)
                .status(true)
                .unitPrice(BigDecimal.valueOf(200000))
                .build();


        Mockito.when(cagesService.getCageById(anyInt())).thenThrow(new AppException(ErrorCode.NOT_FOUND));

        mockMvc.perform(get("/v1/cages/{id}",100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cageRequest)))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Not found"));
    }
}
