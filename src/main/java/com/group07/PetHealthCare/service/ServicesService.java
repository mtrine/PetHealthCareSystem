package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.ServiceCreationRequest;
import com.group07.PetHealthCare.pojo.Services;
import com.group07.PetHealthCare.respositytory.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicesService {
    @Autowired
    private ServiceRepository serviceRepository;
    public Services createService (ServiceCreationRequest request)
    {
        Services service = new Services();
        service.setName(request.getName());
        service.setUnitPrice(request.getUnit_price());
        return serviceRepository.save(service);
    }
}
