


package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.VaccineCreationRequest;
import com.group07.PetHealthCare.dto.request.VaccineUpdateRequest;
import com.group07.PetHealthCare.entity.Vaccine;
import com.group07.PetHealthCare.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service


public class VaccineService {
    @Autowired
    private VaccineRepository vaccinerepository;


    public Vaccine createRequest(VaccineCreationRequest request){
        Vaccine vaccine = new Vaccine();

        vaccine.setName(request.getName());
        vaccine.setExpdate(request.getExpdate());

        return vaccinerepository.save(vaccine);

    }

    public Vaccine updateVaccine(String vaccineid , VaccineUpdateRequest request){
        Vaccine vaccine = getVaccine(vaccineid);

        vaccine.setExpdate(request.getExpdate());

        return vaccinerepository.save(vaccine);

    }


    public void deleteVaccine(String vaccineid){
        vaccinerepository.deleteById(vaccineid);
    }



    public List<Vaccine> getVaccines(){
        return vaccinerepository.findAll();
    }

    public Vaccine getVaccine(String id){
        return vaccinerepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Vaccine not found"));
    }

}