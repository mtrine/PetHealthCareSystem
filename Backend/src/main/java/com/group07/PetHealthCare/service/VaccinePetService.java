package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.VaccinePetRequest;
import com.group07.PetHealthCare.dto.response.VaccinePetResponse;
import com.group07.PetHealthCare.pojo.Pet;
import com.group07.PetHealthCare.pojo.Vaccine;
import com.group07.PetHealthCare.pojo.VaccinePet;
import com.group07.PetHealthCare.respositytory.IPetRepository;
import com.group07.PetHealthCare.respositytory.IVaccinePetRepository;
import com.group07.PetHealthCare.respositytory.IVaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccinePetService {
    @Autowired
    private IVaccinePetRepository vaccinePetRepository;

    @Autowired
    private IPetRepository petRepository;

    @Autowired
    private IVaccineRepository vaccineRepository;
    public VaccinePetResponse addVaccineToPet(VaccinePetRequest request) {
        VaccinePet vaccinePet = new VaccinePet();
        Pet pet = petRepository.findById(request.getPetId()).orElseThrow(()->new RuntimeException("Pet not found"));
        vaccinePet.setPet(pet);
        Vaccine vaccine= vaccineRepository.findById(request.getVaccineId()).orElseThrow(()->new RuntimeException("Vaccine not found"));
        vaccinePet.setVaccine(vaccine);
        vaccinePet.setStingDate(request.getStingDate());
        vaccinePet.setReStingDate(request.getReStingDate());
        return vaccinePetRepository.save(vaccinePet);
    }

    public List<VaccinePetResponse> getVaccinePetByPetId(String petId) {
        Pet pet=petRepository.findById(petId).orElseThrow(()->new RuntimeException("Pet not found"));
        return vaccinePetRepository.findAllByPet(pet);
    }
}
