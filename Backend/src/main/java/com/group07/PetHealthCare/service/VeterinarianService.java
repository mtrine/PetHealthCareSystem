package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.pojo.Customer;
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
        Optional<Veterinarian> veterinarian = veterinarianRepository.findById(Id);
        if(!veterinarian.isPresent()){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return veterinarianRepository.findById(Id);
    }
    public void deleteVeterinarianById(String Id)
    {
        Optional<Veterinarian> veterinarian = veterinarianRepository.findById(Id);
        if(!veterinarian.isPresent()){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        veterinarianRepository.deleteById(Id);
    }

}
