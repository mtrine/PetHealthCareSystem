package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.SpeciesRequest;
import com.group07.PetHealthCare.pojo.Species;
import com.group07.PetHealthCare.respositytory.ISpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpeciesService {
    @Autowired
    private ISpeciesRepository ISpeciesRepository;

    public Species addSpecies(SpeciesRequest species) {
        Species newSpecies =  new Species();
        newSpecies.setName(species.getName());
        return ISpeciesRepository.save(newSpecies);
    }
}
