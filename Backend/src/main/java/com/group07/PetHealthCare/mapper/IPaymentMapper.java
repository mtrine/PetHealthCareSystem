package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.PaymentResponse;
import com.group07.PetHealthCare.pojo.Payment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IPaymentMapper {
    PaymentResponse mapResponse(Payment payment);
    List<PaymentResponse> mapListResponse(List<Payment> payments);
}
