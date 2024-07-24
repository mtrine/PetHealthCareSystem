package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.respositytory.VeterinarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeterinarianService {
    @Autowired
    private VeterinarianRepository veterinarianRepository;


}
