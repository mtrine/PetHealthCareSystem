package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.SessionsRequest;
import com.group07.PetHealthCare.dto.request.VisitScheduleRequest;
import com.group07.PetHealthCare.dto.response.SessionResponse;
import com.group07.PetHealthCare.service.SessionsService;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class SessionsControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private SessionsService sessionsService;

    private ObjectMapper objectMapper;

    private SessionsRequest sessionsRequest;
    private SessionResponse sessionResponse;

    @BeforeEach
    void initData() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        sessionsRequest = new SessionsRequest();
        sessionsRequest.setStartTime(LocalTime.of(9, 0));
        sessionsRequest.setEndTime(LocalTime.of(17, 0));

        sessionResponse = SessionResponse.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .startTime(LocalTime.of(8, 0,0))
                .endTime(LocalTime.of(10, 0,0))
                .build();
    }

    @Test
    @WithMockUser("ADMIN")
    public void addSession() throws Exception {
        when(sessionsService.createSession(sessionsRequest)).thenReturn(sessionResponse);

        mockMvc.perform(post("/v1/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionsRequest)))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser("ADMIN")
    void createSession_InvalidTime() throws Exception {
        when(sessionsService.createSession(any(SessionsRequest.class)))
        .thenThrow(new RuntimeException("Start time must be before end time"));

        String requestJson = objectMapper.writeValueAsString(sessionsRequest);

        mockMvc.perform(post("/v1/sessions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Start time must be before end time"));
    }

    @Test
    @WithMockUser("ADMIN")
    public void getAllSessions() throws Exception {
        List<SessionResponse> sessionResponses = Collections.singletonList(sessionResponse);

        when(sessionsService.getAllSession()).thenReturn(sessionResponses);

        mockMvc.perform(get("/v1/sessions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].id").value("123e4567-e89b-12d3-a456-426614174000"))
                .andExpect(jsonPath("$.result[0].startTime").value("08:00:00"))
                .andExpect(jsonPath("$.result[0].endTime").value("10:00:00"));
    }


}
