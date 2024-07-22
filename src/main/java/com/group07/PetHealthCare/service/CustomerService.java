package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.CustomerCreationRequest;
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

    public Customer createCustomer(CustomerCreationRequest request){

        if (customerRepository.existsByEmail(request.getEmail()) || customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new RuntimeException("User already exists");
        }

        // Tạo đối tượng Customer mới
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setPassword(request.getPassword());
        customer.setAddress(request.getAddress());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setSex(request.getSex());

        // Lưu khách hàng vào cơ sở dữ liệu
        return customerRepository.save(customer);
    }

    public Optional<Customer> loginCustomer(CustomerCreationRequest request){
        Optional<Customer> customer=customerRepository.findCustomerByEmail(request.getEmail());
        if(!customer.isPresent()){
            throw new RuntimeException("Email incorrect");
        }
        if(!request.getPassword().equals(customer.get().getPassword())){
            throw new RuntimeException("Password incorrect");
        }
        return customer;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }
}

