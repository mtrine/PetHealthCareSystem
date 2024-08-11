package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.HospitalizationRequest;
import com.group07.PetHealthCare.dto.response.HospitalizationResponse;
import com.group07.PetHealthCare.pojo.Cage;
import com.group07.PetHealthCare.pojo.Hospitalization;
import com.group07.PetHealthCare.pojo.Pet;
import com.group07.PetHealthCare.respositytory.ICageRepository;
import com.group07.PetHealthCare.respositytory.IHospitalizationRepository;
import com.group07.PetHealthCare.respositytory.IPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalizationService {
    @Autowired
    private IHospitalizationRepository hospitalizationRepository;

    @Autowired
    private ICageRepository cageRepository;

    @Autowired
    private IPetRepository petRepository;

    public HospitalizationResponse createHospitalization(HospitalizationRequest request) {
        if (request.getCageNumber() == null) {
            throw new IllegalArgumentException("Cage number  must not be null");
        }
    if (request.getPetID()==null){
        throw new IllegalArgumentException("Pet ID must not be null");
    }
        Hospitalization hospitalization = new Hospitalization();
        hospitalization.setReasonForHospitalization(request.getReasonForHospitalization());
        hospitalization.setStartDate(request.getStartDate());
        hospitalization.setHealthCondition(request.getHealthCondition());

        Cage cage = cageRepository.findById(request.getCageNumber())
                .orElseThrow(() -> new RuntimeException("Cage number not found"));
        Pet pet = petRepository.findById(request.getPetID())
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        hospitalization.setCage(cage);
        cage.setStatus(!cage.getStatus());
        hospitalization.setPetID(pet);

        return hospitalizationRepository.save(hospitalization);
    }

    public List<HospitalizationResponse> getAllHospitalization() {
        return hospitalizationRepository.findAll();
    }

    public HospitalizationResponse getHospitalizationById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        return hospitalizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospitalization not found"));
    }

    public HospitalizationResponse updateHospitalization(String id, HospitalizationRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }

        Hospitalization hospitalization = getHospitalizationById(id);
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
        return hospitalizationRepository.save(hospitalization);
    }

    public void deleteHospitalization(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }

        Hospitalization hospitalization = getHospitalizationById(id);
        hospitalizationRepository.delete(hospitalization);
    }

    public List<HospitalizationResponse> getHospitalizationByPetId(String petId) {
        if (petId == null) {
            throw new IllegalArgumentException("Pet ID must not be null");
        }

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        return hospitalizationRepository.findAllByPetID(pet);
    }
}
