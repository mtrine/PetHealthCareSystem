package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.VaccineRequest;
import com.group07.PetHealthCare.dto.response.VaccineResponse;
import com.group07.PetHealthCare.pojo.Vaccine;
import com.group07.PetHealthCare.respositytory.IVaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VaccineService {
    @Autowired
    private IVaccineRepository vaccinerepository;

    public VaccineResponse createVaccine(VaccineRequest request){
        Vaccine vaccine = new Vaccine();

        vaccine.setName(request.getName());
        vaccine.setExpDate(request.getExpDate());

        return vaccinerepository.save(vaccine);

    }

    public VaccineResponse updateVaccine(String vaccineid , VaccineRequest request){
        Vaccine vaccine = getVaccine(vaccineid);

        vaccine.setExpDate(request.getExpDate());

        return vaccinerepository.save(vaccine);

    }

    public void deleteVaccine(String vaccineid){
        Vaccine vaccine = getVaccine(vaccineid);
        vaccinerepository.delete(vaccine);
    }

    public List<VaccineResponse> getVaccines(){
        return vaccinerepository.findAll();
    }

    public VaccineResponse getVaccine(String id){
        return vaccinerepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Vaccine not found"));
    }


}
