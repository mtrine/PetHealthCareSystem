package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.respositytory.CustomerRepository;
import com.group07.PetHealthCare.respositytory.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service

public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PetRepository petRepository;
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if(!customer.isPresent()){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return customerRepository.findById(id);
    }

    @Transactional
    public void deleteCustomersById(String id){
        Optional<Customer> customer = customerRepository.findById(id);
        if(!customer.isPresent()){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        customerRepository.deleteById(id);

    }
}

