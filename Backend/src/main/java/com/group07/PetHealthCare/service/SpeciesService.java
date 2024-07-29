package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.SpeciesRequest;
import com.group07.PetHealthCare.pojo.Species;
import com.group07.PetHealthCare.respositytory.SpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpeciesService {
    @Autowired
    private SpeciesRepository speciesRepository;

    public Species addSpecies(SpeciesRequest species) {
        Species newSpecies =  new Species();
        newSpecies.setName(species.getName());
        return speciesRepository.save(newSpecies);
    }
}
