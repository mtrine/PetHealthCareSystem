package com.group07.PetHealthCare.controllers;

import com.group07.PetHealthCare.dto.request.ApiResponse;
import com.group07.PetHealthCare.dto.request.PaymentRequest;
import com.group07.PetHealthCare.dto.request.RefundRequest;
import com.group07.PetHealthCare.dto.response.PaymentResponse;
import com.group07.PetHealthCare.dto.response.VNPayResponse;
import com.group07.PetHealthCare.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v1/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    String baseUrl="";
    @PostMapping("/vn-pay")
    public ApiResponse<VNPayResponse> pay(HttpServletRequest request,@RequestBody PaymentRequest paymentRequest) {

         baseUrl = request.getHeader("Referer");
        ApiResponse<VNPayResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(paymentService.createVnPayPayment(request,paymentRequest));
        return apiResponse;
    }

    @GetMapping("/vn-pay-callback")
    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        paymentService.payCallbackHandler(request,response,baseUrl);
    }

    @PostMapping("/cash")
    public ApiResponse<PaymentResponse> cash(@RequestBody PaymentRequest paymentRequest) {
        ApiResponse<PaymentResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(paymentService.createCashPayment(paymentRequest));
        return apiResponse;
    }

    @PostMapping("/refund")
    public ApiResponse<PaymentResponse> refund(@RequestBody RefundRequest refundRequest) {
        ApiResponse<PaymentResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(paymentService.refund(refundRequest));
        return apiResponse;
    }

    @GetMapping("/revenue/day")
    public ApiResponse<Map<LocalDate, Double>> getRevenueByDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        ApiResponse<Map<LocalDate, Double>> apiResponse = new ApiResponse<>();
        Map<LocalDate, Double> revenue = paymentService.getRevenueByDay(startDate, endDate);
        apiResponse.setResult(revenue);
        return apiResponse;
    }

    @GetMapping("/revenue/month")
    public ApiResponse<Map<Integer, Double>> getRevenueByMonth(@RequestParam int year) {
        ApiResponse<Map<Integer, Double>> apiResponse = new ApiResponse<>();
        Map<Integer, Double> revenue = paymentService.getRevenueByMonth(year);
        apiResponse.setResult(revenue);
        return apiResponse;
    }


    @GetMapping("/revenue/year")
    public ApiResponse<Map<Integer, Double>> getRevenueByYear(
          @RequestParam int endYear) {
        ApiResponse<Map<Integer, Double>> apiResponse = new ApiResponse<>();
        Map<Integer, Double> revenue = paymentService.getRevenueByYear( endYear);
        apiResponse.setResult(revenue);
        return apiResponse;
    }

}
