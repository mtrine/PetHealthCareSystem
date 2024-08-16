package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.ServiceRequest;
import com.group07.PetHealthCare.dto.response.ServicesResponse;
import com.group07.PetHealthCare.mapper.IServiceMapper;
import com.group07.PetHealthCare.pojo.Services;
import com.group07.PetHealthCare.respositytory.IServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesService {
    @Autowired
    private IServiceRepository IServiceRepository;

    @Autowired
    private IServiceMapper serviceMapper;
    public ServicesResponse createService (ServiceRequest request)
    {
        Services service = new Services();
        service.setName(request.getName());
        service.setUnitPrice(request.getUnitPrice());
        return serviceMapper.toServicesResponse(IServiceRepository.save(service));
    }

    public List<ServicesResponse> getAllServices(){

        return serviceMapper.toServicesResponseList(IServiceRepository.findAll());
    }
}
