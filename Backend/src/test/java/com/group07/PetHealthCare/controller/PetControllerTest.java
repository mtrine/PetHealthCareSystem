package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group07.PetHealthCare.dto.request.PetCreationRequest;
import com.group07.PetHealthCare.dto.request.PetUpdateRequest;
import com.group07.PetHealthCare.dto.response.CustomerResponse;
import com.group07.PetHealthCare.dto.response.PetResponse;
import com.group07.PetHealthCare.dto.response.SpeciesResponse;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.service.PetService;
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

import java.util.Collections;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetService petService;

    private ObjectMapper objectMapper;
    private PetCreationRequest petCreationRequest;
    private PetUpdateRequest petUpdateRequest;
    private PetResponse petResponse;
    private CustomerResponse customerResponse;

    @BeforeEach
    void initData() {
        objectMapper = new ObjectMapper();

        petCreationRequest = new PetCreationRequest();
        petCreationRequest.setName("Bông");
        petCreationRequest.setAge(3);
        petCreationRequest.setSpeciesID("971c1a50-e074-43d3-a8db-6e7f5bd1a9c2");
        petCreationRequest.setCustomerID("e80bac44-998c-44db-b548-2d03b12e8a25");

        petUpdateRequest = new PetUpdateRequest();
        petUpdateRequest.setName("Bông");
        petUpdateRequest.setAge(4);
        petUpdateRequest.setSpeciesID("971c1a50-e074-43d3-a8db-6e7f5bd1a9c2");

        SpeciesResponse speciesResponse = new SpeciesResponse();
        speciesResponse.setId("971c1a50-e074-43d3-a8db-6e7f5bd1a9c2");
        speciesResponse.setName("Dog");

        customerResponse = new CustomerResponse();
        customerResponse.setId("e80bac44-998c-44db-b548-2d03b12e8a25");
        customerResponse.setName("Nguyen Ngoc Quynh Nhu");
        customerResponse.setEmail("nhuquynh6453@gmail.com");

        petResponse = new PetResponse();
        petResponse.setId("f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9");
        petResponse.setName("Bông");
        petResponse.setAge(3);
        petResponse.setSpeciesResponse(speciesResponse);
        petResponse.setGender(true);
        petResponse.setCustomerResponse(customerResponse);
    }

    @Test
    @WithMockUser("CUSTOMER")
    void createPet() throws Exception {
        when(petService.addPet(any(PetCreationRequest.class))).thenReturn(petResponse);

        mockMvc.perform(post("/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petCreationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value("f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9"))
                .andExpect(jsonPath("$.result.name").value("Bông"))
                .andExpect(jsonPath("$.result.age").value(3))
                .andExpect(jsonPath("$.result.speciesResponse.id").value("971c1a50-e074-43d3-a8db-6e7f5bd1a9c2"))
                .andExpect(jsonPath("$.result.speciesResponse.name").value("Dog"))
                .andExpect(jsonPath("$.result.customerResponse.id").value("e80bac44-998c-44db-b548-2d03b12e8a25"))
                .andExpect(jsonPath("$.result.customerResponse.name").value("Nguyen Ngoc Quynh Nhu"));
    }

    @Test
    @WithMockUser("CUSTOMER")
    void updatePet() throws Exception {
        String requestJson = objectMapper.writeValueAsString(petUpdateRequest);

        when(petService.updatePet(any(String.class), any(PetUpdateRequest.class))).thenReturn(petResponse);

        mockMvc.perform(patch("/v1/pets/{id}", "f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value("f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9"))
                .andExpect(jsonPath("$.result.name").value("Bông"))
                .andExpect(jsonPath("$.result.age").value(3))
                .andExpect(jsonPath("$.result.speciesResponse.id").value("971c1a50-e074-43d3-a8db-6e7f5bd1a9c2"))
                .andExpect(jsonPath("$.result.speciesResponse.name").value("Dog"))
                .andExpect(jsonPath("$.result.customerResponse.id").value("e80bac44-998c-44db-b548-2d03b12e8a25"))
                .andExpect(jsonPath("$.result.customerResponse.name").value("Nguyen Ngoc Quynh Nhu"));
    }

    @Test
    @WithMockUser("CUSTOMER")
    void getPetById() throws Exception {
        when(petService.getPetById("f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9")).thenReturn(petResponse);

        mockMvc.perform(get("/v1/pets/{id}", "f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value("f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9"))
                .andExpect(jsonPath("$.result.name").value("Bông"))
                .andExpect(jsonPath("$.result.age").value(3))
                .andExpect(jsonPath("$.result.speciesResponse.id").value("971c1a50-e074-43d3-a8db-6e7f5bd1a9c2"))
                .andExpect(jsonPath("$.result.speciesResponse.name").value("Dog"))
                .andExpect(jsonPath("$.result.customerResponse.id").value("e80bac44-998c-44db-b548-2d03b12e8a25"))
                .andExpect(jsonPath("$.result.customerResponse.name").value("Nguyen Ngoc Quynh Nhu"));
    }

    @Test
    @WithMockUser("CUSTOMER")
    void deletePet() throws Exception {
        Mockito.doNothing().when(petService).deletePet("f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9");

        mockMvc.perform(delete("/v1/pets/{id}", "f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Pet deleted successfully"));
    }

    @Test
    @WithMockUser("CUSTOMER")
    void getPetsByCustomerId() throws Exception {
        Set<PetResponse> petResponses = Collections.singleton(petResponse);
        when(petService.getPetsByCustomerId("e80bac44-998c-44db-b548-2d03b12e8a25")).thenReturn(petResponses);

        mockMvc.perform(get("/v1/pets/customers/{customerId}", "e80bac44-998c-44db-b548-2d03b12e8a25")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result[0].id").value("f7028ff2-2bbd-4943-8d1b-b3e3a4cac2e9"))
                .andExpect(jsonPath("$.result[0].name").value("Bông"))
                .andExpect(jsonPath("$.result[0].age").value(3))
                .andExpect(jsonPath("$.result[0].speciesResponse.id").value("971c1a50-e074-43d3-a8db-6e7f5bd1a9c2"))
                .andExpect(jsonPath("$.result[0].speciesResponse.name").value("Dog"))
                .andExpect(jsonPath("$.result[0].customerResponse.id").value("e80bac44-998c-44db-b548-2d03b12e8a25"))
                .andExpect(jsonPath("$.result[0].customerResponse.name").value("Nguyen Ngoc Quynh Nhu"));
    }

    @Test
    @WithMockUser("CUSTOMER")
    void addPet_SpecieNotFound() throws Exception{
        Mockito.when(petService.addPet(any(PetCreationRequest.class)))
                .thenThrow(new RuntimeException("Species not found"));

        String requestJson = objectMapper.writeValueAsString(petCreationRequest);
        mockMvc.perform(post("/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Species not found"));
    }



    @Test
    @WithMockUser("CUSTOMER")
    void addPet_CustomerNotFound() throws Exception{
        Mockito.when(petService.addPet(any(PetCreationRequest.class)))
                .thenThrow(new RuntimeException("Customer not found"));

        String requestJson = objectMapper.writeValueAsString(petCreationRequest);
        mockMvc.perform(post("/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Customer not found"));
    }

    @Test
    @WithMockUser("CUSTOMER")
    void getPetByCustomerId_CustomerNotFound() throws Exception{
        Mockito.when(petService.getPetsByCustomerId(anyString()))
                .thenThrow(new RuntimeException("Customer not found"));

        String requestJson = objectMapper.writeValueAsString(petCreationRequest);
        mockMvc.perform(get("/v1/pets/customers/{customerId}","invalid-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Customer not found"));
    }

    @Test
    @WithMockUser("CUSTOMER")
    void updatePet_PetNotFound() throws Exception{
        Mockito.when(petService.updatePet(anyString(),any(PetUpdateRequest.class)))
                .thenThrow(new RuntimeException("Pet not found"));

        String requestJson = objectMapper.writeValueAsString(petCreationRequest);
        mockMvc.perform(patch("/v1/pets/{id}","invalid-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Pet not found"));
    }

    @Test
    @WithMockUser("CUSTOMER")
    void deletePet_PetNotFound() throws Exception{
        Mockito.doThrow(new AppException(ErrorCode.NOT_FOUND))
                .when(petService).deletePet(anyString());


        String requestJson = objectMapper.writeValueAsString(petCreationRequest);
        mockMvc.perform(delete("/v1/pets/{id}","invalid-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not found"));
    }

    @Test
    @WithMockUser("CUSTOMER")
    void addMyPet_CustomerNotFound() throws Exception{
        Mockito.when(petService.addMyPet(any(PetCreationRequest.class)))
                .thenThrow(new AppException(ErrorCode.NOT_FOUND));


        String requestJson = objectMapper.writeValueAsString(petCreationRequest);
        mockMvc.perform(post("/v1/pets/my-pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not found"));
    }

    @Test
    @WithMockUser("CUSTOMER")
    void addMyPet_SpeciesNotFound() throws Exception{
        Mockito.when(petService.addMyPet(any(PetCreationRequest.class)))
                .thenThrow(new IllegalArgumentException("Species not found"));


        String requestJson = objectMapper.writeValueAsString(petCreationRequest);
        mockMvc.perform(post("/v1/pets/my-pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Species not found"));
    }

    @Test
    @WithMockUser("CUSTOMER")
    void getMyPetList_NotFound() throws Exception{
        Mockito.when(petService.getMyPetList())
                .thenThrow(new AppException(ErrorCode.NOT_FOUND));


        String requestJson = objectMapper.writeValueAsString(petCreationRequest);
        mockMvc.perform(get("/v1/pets/my-pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not found"));
    }

    @Test
    @WithMockUser("CUSTOMER")
    void getPetById_NotFound() throws Exception{
        Mockito.when(petService.getPetById(anyString()))
                .thenThrow(new AppException(ErrorCode.NOT_FOUND));


        String requestJson = objectMapper.writeValueAsString(petCreationRequest);
        mockMvc.perform(get("/v1/pets/{id}","invalid-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not found"));
    }

    @Test
    @WithMockUser("CUSTOMER")
    void getPetByCustomerEmail_NotFound() throws Exception{
        Mockito.when(petService.getPetByCustomerEmail(anyString()))
                .thenThrow(new AppException(ErrorCode.NOT_FOUND));


        String requestJson = objectMapper.writeValueAsString(petCreationRequest);
        mockMvc.perform(get("/v1/pets/customer-email/{email}","invalidemail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Not found"));
    }


}
