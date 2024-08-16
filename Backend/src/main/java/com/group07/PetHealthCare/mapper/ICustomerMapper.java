package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.CustomerResponse;
import com.group07.PetHealthCare.pojo.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICustomerMapper {
    CustomerResponse toCustomerResponse(Customer customer);
    List<CustomerResponse> toCustomerResponses(List<Customer> customers);
    Customer toCustomer(CustomerResponse customerResponse);
}
