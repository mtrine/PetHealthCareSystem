package com.group07.PetHealthCare.service;
import com.group07.PetHealthCare.dto.request.CageRequest;
import com.group07.PetHealthCare.dto.response.CageResponse;
import com.group07.PetHealthCare.mapper.ICageMapper;
import com.group07.PetHealthCare.pojo.Cage;
import com.group07.PetHealthCare.respositytory.ICageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CagesService {
    @Autowired
    private ICageRepository cagesRepository;
    @Autowired
    private ICageMapper cageMapper;
    public CageResponse addCage(CageRequest request) {
        Cage cage = new Cage();
        cage.setUnitPrice(request.getUnitPrice());
        return cageMapper.toResponse(cagesRepository.save(cage)) ;
    }

    public List<CageResponse> getAllCages() {

        return cageMapper.toResponseList(cagesRepository.findAll());
    }
}
