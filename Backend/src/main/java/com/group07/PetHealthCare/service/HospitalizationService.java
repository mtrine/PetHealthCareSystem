package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.HospitalizationRequest;
import com.group07.PetHealthCare.dto.response.HospitalizationResponse;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.mapper.IHospitalizationMapper;
import com.group07.PetHealthCare.pojo.*;
import com.group07.PetHealthCare.respositytory.ICageRepository;
import com.group07.PetHealthCare.respositytory.ICustomerRepository;
import com.group07.PetHealthCare.respositytory.IHospitalizationRepository;
import com.group07.PetHealthCare.respositytory.IPetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HospitalizationService {
    @Autowired
    private IHospitalizationRepository hospitalizationRepository;

    @Autowired
    private ICageRepository cageRepository;

    @Autowired
    private IPetRepository petRepository;

    @Autowired
    private IHospitalizationMapper hospitalizationMapper;

    @Autowired
    private ICustomerRepository customerRepository;
    public HospitalizationResponse createHospitalization(HospitalizationRequest request) {
        if (request.getCageNumber() == null) {
            throw new IllegalArgumentException("Cage number  must not be null");
        }
    if (request.getPetID()==null){
        throw new IllegalArgumentException("Pet ID must not be null");
    }
        Hospitalization hospitalization = new Hospitalization();
        hospitalization.setReasonForHospitalization(request.getReasonForHospitalization());
        hospitalization.setStartDate(LocalDate.now());
        hospitalization.setHealthCondition(request.getHealthCondition());

        Cage cage = cageRepository.findById(request.getCageNumber())
                .orElseThrow(() -> new RuntimeException("Cage number not found"));
        Pet pet = petRepository.findById(request.getPetID())
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        hospitalization.setCage(cage);
        cage.setStatus(!cage.getStatus());
        hospitalization.setPetID(pet);

        return hospitalizationMapper.toHospitalizationResponse(hospitalizationRepository.save(hospitalization));
    }

    public List<HospitalizationResponse> getAllHospitalization() {

        return hospitalizationMapper.toHospitalizationResponseList(hospitalizationRepository.findAll());
    }

    public HospitalizationResponse getHospitalizationById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        return hospitalizationMapper.toHospitalizationResponse(hospitalizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospitalization not found")));
    }

    public HospitalizationResponse updateHospitalization(String id, HospitalizationRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }

        Hospitalization hospitalization = hospitalizationRepository.findById(id).orElseThrow(() -> new RuntimeException("Hospitalization not found"));
        if (request.getReasonForHospitalization() != null) {
            hospitalization.setReasonForHospitalization(request.getReasonForHospitalization());
        }
        if (request.getStartDate() != null) {
            hospitalization.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            hospitalization.setEndDate(request.getEndDate());
        }
        if (request.getHealthCondition() != null) {
            hospitalization.setHealthCondition(request.getHealthCondition());
        }
        if (request.getCageNumber() != null) {
            Cage cage = cageRepository.findById(request.getCageNumber())
                    .orElseThrow(() -> new RuntimeException("Cage number not found"));
            hospitalization.setCage(cage);
        }
        if (request.getPetID() != null) {
            Pet pet = petRepository.findById(request.getPetID())
                    .orElseThrow(() -> new RuntimeException("Pet not found"));
            hospitalization.setPetID(pet);
        }
        return hospitalizationMapper.toHospitalizationResponse(hospitalizationRepository.save(hospitalization));
    }

    public void deleteHospitalization(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }

        Hospitalization hospitalization = hospitalizationRepository.findById(id).orElseThrow(() -> new RuntimeException("Hospitalization not found"));
        hospitalizationRepository.delete(hospitalization);
    }

    public List<HospitalizationResponse> getHospitalizationByPetId(String petId) {
        if (petId == null) {
            throw new IllegalArgumentException("Pet ID must not be null");
        }

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        return hospitalizationMapper.toHospitalizationResponseList( hospitalizationRepository.findAllByPetID(pet));
    }

    public List<HospitalizationResponse> getHospitalizationByCustomerId(String customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID must not be null");
        }
        Customer customer=customerRepository.findById(customerId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        Set<Pet> pets= customer.getPets();

        List<Hospitalization> hospitalizations = pets.stream()
                .flatMap(pet -> hospitalizationRepository.findAllByPetID(pet).stream())
                .collect(Collectors.toList());
        return hospitalizationMapper.toHospitalizationResponseList(hospitalizations);
    }

    public List<HospitalizationResponse> getMyHospitalization() {
        SecurityContext context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        log.info(email);
        Customer customer=customerRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        return getHospitalizationByCustomerId(customer.getId());
    }
}
