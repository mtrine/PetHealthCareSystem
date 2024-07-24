package com.group07.PetHealthCare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group07.PetHealthCare.dto.request.UserCreationRequest;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
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

    public Customer createCustomer(UserCreationRequest request){

        if (customerRepository.existsByEmail(request.getEmail()) || customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.CUSTOME_EXISTED);
        }
        ObjectMapper objectMapper = new ObjectMapper();
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

    public Optional<Customer> loginCustomer(UserCreationRequest request){
        Optional<Customer> customer=customerRepository.findCustomerByEmail(request.getEmail());
        if(!customer.isPresent()){
            throw new AppException(ErrorCode.EMAIL_INCORRECT);
        }
        if(!request.getPassword().equals(customer.get().getPassword())){
            throw new AppException(ErrorCode.PASS_INCORRECT);
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

