package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
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
    private IPetRepository IPetRepository;
    public List<Customer> getAllCustomers() {
        return ICustomerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String id) {
        Optional<Customer> customer = ICustomerRepository.findById(id);
        if(!customer.isPresent()){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return ICustomerRepository.findById(id);
    }

    @Transactional
    public void deleteCustomersById(String id){
        Optional<Customer> customer = ICustomerRepository.findById(id);
        if(!customer.isPresent()){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        ICustomerRepository.deleteById(id);

    }
}

