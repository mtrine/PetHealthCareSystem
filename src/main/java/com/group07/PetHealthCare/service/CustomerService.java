package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.CustomerCreationRequest;
import com.group07.PetHealthCare.pojo.Customer;
import com.group07.PetHealthCare.respositytory.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(CustomerCreationRequest request){
        Customer customer= new Customer();

        customer.setName(request.getName());
        customer.setPassword(request.getPassword());
        customer.setAddress(request.getAddress());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setSex(request.getSex());
        return customerRepository.save(customer);

    }
}
