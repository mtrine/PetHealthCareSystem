package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.response.VNPayResponse;
import com.group07.PetHealthCare.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payments")
public class VnpayController {
    @Autowired
    private PaymentService paymentService;
    @GetMapping("/vn-pay")
    public ApiResponse<VNPayResponse> pay(HttpServletRequest request) {
        ApiResponse<VNPayResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(paymentService.createVnPayPayment(request));
        return apiResponse;
    }

    @GetMapping("/vn-pay-callback")
    public ApiResponse<VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        ApiResponse<VNPayResponse> apiResponse = new ApiResponse<>();
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            apiResponse.setResult(new VNPayResponse("00","Success",""));
            return apiResponse;
        } else {
            throw new RuntimeException("Failed");
        }
    }
}
