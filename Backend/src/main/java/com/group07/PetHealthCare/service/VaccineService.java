package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.VaccineRequest;
import com.group07.PetHealthCare.pojo.Vaccine;
import com.group07.PetHealthCare.respositytory.IVaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VaccineService {
    @Autowired
    private IVaccineRepository vaccinerepository;

    public Vaccine createVaccine(VaccineRequest request){
        Vaccine vaccine = new Vaccine();

        vaccine.setName(request.getName());
        vaccine.setExpDate(request.getExpDate());

        return vaccinerepository.save(vaccine);

    }

    public Vaccine updateVaccine(String vaccineid , VaccineRequest request){
        Vaccine vaccine = getVaccine(vaccineid);

        vaccine.setExpDate(request.getExpDate());

        return vaccinerepository.save(vaccine);

    }

    public void deleteVaccine(String vaccineid){
        Vaccine vaccine = getVaccine(vaccineid);
        vaccinerepository.delete(vaccine);
    }

    public List<Vaccine> getVaccines(){
        return vaccinerepository.findAll();
    }

    public Vaccine getVaccine(String id){
        return vaccinerepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Vaccine not found"));
    }


}
