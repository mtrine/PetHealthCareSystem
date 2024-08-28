package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.PaymentRequest;
import com.group07.PetHealthCare.dto.response.VNPayResponse;
import com.group07.PetHealthCare.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@Slf4j
@RestController
@RequestMapping("/v1/payments")
public class VnpayController {
    private String port="";
    @Autowired
    private PaymentService paymentService;
    @PostMapping("/vn-pay")
    public ApiResponse<VNPayResponse> pay(HttpServletRequest request,@RequestBody PaymentRequest paymentRequest) {
        port=request.getParameter("port");
        log.info(port);
        ApiResponse<VNPayResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(paymentService.createVnPayPayment(request,paymentRequest));
        return apiResponse;
    }

    @GetMapping("/vn-pay-callback")
    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        paymentService.payCallbackHandler(request,response,port);
    }
}
