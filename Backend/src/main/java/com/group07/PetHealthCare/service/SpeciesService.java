package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.SpeciesRequest;
import com.group07.PetHealthCare.dto.response.SpeciesResponse;
import com.group07.PetHealthCare.mapper.ISpeciesMapper;
import com.group07.PetHealthCare.pojo.Species;
import com.group07.PetHealthCare.respositytory.ISpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpeciesService {
    @Autowired
    private ISpeciesRepository ISpeciesRepository;
    @Autowired
    private ISpeciesMapper speciesMapper;
    public SpeciesResponse addSpecies(SpeciesRequest species) {
        Species newSpecies =  new Species();
        newSpecies.setName(species.getName());
        return speciesMapper.toSpeciesResponse(ISpeciesRepository.save(newSpecies));
    }
    public List<SpeciesResponse> getAllSpecies() {

        return speciesMapper.toSpeciesResponseList(ISpeciesRepository.findAll());
    }
}
