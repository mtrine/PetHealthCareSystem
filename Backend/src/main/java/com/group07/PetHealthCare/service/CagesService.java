package com.group07.PetHealthCare.service;
import com.group07.PetHealthCare.dto.request.CageRequest;
import com.group07.PetHealthCare.pojo.Cage;
import com.group07.PetHealthCare.respositytory.ICageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CagesService {
    @Autowired
    private ICageRepository cagesRepository;

    public Cage addCage(CageRequest request) {
        Cage cage = new Cage();
        cage.setUnitPrice(request.getUnitPrice());
        return cagesRepository.save(cage);
    }

    public List<Cage> getAllCages() {
        return cagesRepository.findAll();
    }
}
