package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.VaccineRequest;
import com.group07.PetHealthCare.dto.response.VaccineResponse;
import com.group07.PetHealthCare.mapper.IVaccineMapper;
import com.group07.PetHealthCare.mapper.IVaccinePetMapper;
import com.group07.PetHealthCare.pojo.Vaccine;
import com.group07.PetHealthCare.respositytory.IVaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VaccineService {
    @Autowired
    private IVaccineRepository vaccineRepository;

    @Autowired
    private IVaccineMapper vaccineMapper;
    public VaccineResponse createVaccine(VaccineRequest request){
        Vaccine vaccine = new Vaccine();

        vaccine.setName(request.getName());
        vaccine.setExpDate(request.getExpDate());

        return vaccineMapper.toVaccineResponse(vaccineRepository.save(vaccine));

    }

    public VaccineResponse updateVaccine(String vaccineid , VaccineRequest request){
        Vaccine vaccine = vaccineRepository.findById(vaccineid).orElseThrow(()->new RuntimeException("Vaccine not found"));

        vaccine.setExpDate(request.getExpDate());

        return vaccineMapper.toVaccineResponse(vaccineRepository.save(vaccine));

    }

    public void deleteVaccine(String vaccineid){
        Vaccine vaccine = vaccineRepository.findById(vaccineid).orElseThrow(()->new RuntimeException("Vaccine not found"));
        vaccineRepository.delete(vaccine);
    }

    public List<VaccineResponse> getVaccines(){
        return vaccineMapper.toVaccineResponses(vaccineRepository.findAll());
    }

    public VaccineResponse getVaccine(String id){
        return vaccineMapper.toVaccineResponse(vaccineRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Vaccine not found")));
    }


}
