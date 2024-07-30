package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.ServiceRequest;
import com.group07.PetHealthCare.pojo.Services;
import com.group07.PetHealthCare.respositytory.IServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicesService {
    @Autowired
    private IServiceRepository IServiceRepository;
    public Services createService (ServiceRequest request)
    {
        Services service = new Services();
        service.setName(request.getName());
        service.setUnitPrice(request.getUnit_price());
        return IServiceRepository.save(service);
    }
}
