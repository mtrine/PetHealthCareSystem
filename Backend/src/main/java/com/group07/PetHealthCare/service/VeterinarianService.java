package com.group07.PetHealthCare.service;


import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.pojo.Veterinarian;
import com.group07.PetHealthCare.respositytory.IVeterinarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeterinarianService {
    @Autowired
    private IVeterinarianRepository IVeterinarianRepository;

    public List<VeterinarianResponse> getAllVeterinarian() {return IVeterinarianRepository.findAll();}

    public Optional<VeterinarianResponse> getVeterinarianById (String Id)
    {
        Optional<Veterinarian> veterinarian = IVeterinarianRepository.findById(Id);
        if(!veterinarian.isPresent()){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return IVeterinarianRepository.findById(Id);
    }
    public void deleteVeterinarianById(String Id)
    {
        Optional<Veterinarian> veterinarian = IVeterinarianRepository.findById(Id);
        if(!veterinarian.isPresent()){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        IVeterinarianRepository.deleteById(Id);
    }

}
