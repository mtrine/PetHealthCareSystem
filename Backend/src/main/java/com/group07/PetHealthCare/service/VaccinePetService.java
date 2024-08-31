package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.VaccinePetRequest;
import com.group07.PetHealthCare.dto.response.VaccinePetResponse;
import com.group07.PetHealthCare.mapper.IVaccinePetMapper;
import com.group07.PetHealthCare.pojo.Pet;
import com.group07.PetHealthCare.pojo.Vaccine;
import com.group07.PetHealthCare.pojo.VaccinePet;
import com.group07.PetHealthCare.respositytory.IPetRepository;
import com.group07.PetHealthCare.respositytory.IVaccinePetRepository;
import com.group07.PetHealthCare.respositytory.IVaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Autowired
    private IVaccinePetMapper vaccinePetMapper;

    @PreAuthorize("hasAnyRole('STAFF','ADMIN','VETERINARIAN')")
    public VaccinePetResponse addVaccineToPet(VaccinePetRequest request) {
        // Tìm Pet từ request
        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        // Tìm Vaccine từ request
        Vaccine vaccine = vaccineRepository.findById(request.getVaccineId())
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));

        // Tìm VaccinePet với Pet và Vaccine đã cho
        VaccinePet existingVaccinePet = vaccinePetRepository.findByPetAndVaccine(pet, vaccine);

        if (existingVaccinePet != null) {
            // Nếu VaccinePet đã tồn tại, cập nhật ngày chích và ngày chích lại
            existingVaccinePet.setStingDate(request.getStingDate());
            existingVaccinePet.setReStingDate(request.getReStingDate());
            return vaccinePetMapper.toVaccinePetResponse(vaccinePetRepository.save(existingVaccinePet));
        } else {
            // Nếu VaccinePet chưa tồn tại, tạo mới
            VaccinePet vaccinePet = new VaccinePet();
            vaccinePet.setPet(pet);
            vaccinePet.setVaccine(vaccine);
            vaccinePet.setStingDate(request.getStingDate());
            vaccinePet.setReStingDate(request.getReStingDate());
            return vaccinePetMapper.toVaccinePetResponse(vaccinePetRepository.save(vaccinePet));
        }
    }


    public List<VaccinePetResponse> getVaccinePetByPetId(String petId) {
        Pet pet=petRepository.findById(petId).orElseThrow(()->new RuntimeException("Pet not found"));
        return vaccinePetMapper.toVaccinePetResponseList(vaccinePetRepository.findAllByPet(pet));
    }
}
