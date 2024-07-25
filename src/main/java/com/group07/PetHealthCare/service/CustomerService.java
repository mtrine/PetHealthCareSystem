package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.respositytory.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service

public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public void deleteCustomersById(String id){
        customerRepository.deleteById(id);
    }
}

