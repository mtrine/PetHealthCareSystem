package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.response.CustomerResponse;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.mapper.ICustomerMapper;
import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.respositytory.ICustomerRepository;
import com.group07.PetHealthCare.respositytory.IPetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service

public class CustomerService {
    @Autowired
    private ICustomerRepository ICustomerRepository;
    @Autowired
    private ICustomerMapper customerMapper;
    public List<CustomerResponse> getAllCustomers() {

        return customerMapper.toCustomerResponses(ICustomerRepository.findAll());
    }

    public CustomerResponse getCustomerById(String id) {
        Optional<Customer> customer = ICustomerRepository.findById(id);
        if(!customer.isPresent()){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return customerMapper.toCustomerResponse(customer.get());
    }


}

