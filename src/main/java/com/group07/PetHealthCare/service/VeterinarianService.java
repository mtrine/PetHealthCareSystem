package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.pojo.Veterinarian;
import com.group07.PetHealthCare.respositytory.VeterinarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeterinarianService {
    @Autowired
    private VeterinarianRepository veterinarianRepository;

    public List<Veterinarian> getAllVeterinarian() {return veterinarianRepository.findAll();}

    public Optional<Veterinarian> getVeterinarianById (String Id)
    {
        return veterinarianRepository.findById(Id);
    }
    public void deleteVeterinarianById(String Id)
    {
        veterinarianRepository.deleteById(Id);
    }

}
