package com.group07.PetHealthCare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.group07.PetHealthCare.dto.request.PaymentRequest;
import com.group07.PetHealthCare.dto.request.RefundRequest;
import com.group07.PetHealthCare.dto.request.SessionsRequest;
import com.group07.PetHealthCare.dto.response.*;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private ObjectMapper objectMapper;
    private PaymentRequest paymentRequestforHos;
    private PaymentRequest paymentRequestforAppointment;
    private PaymentResponse paymentResponse;
    private PaymentRequest invalidPaymentRequest;
    private RefundRequest refundRequest;
    private VNPayResponse vnPayResponse;
    private Map<LocalDate, Double> dailyRevenue;

    @BeforeEach
    void initData() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        paymentRequestforHos = new PaymentRequest();
        paymentRequestforHos.setPaymentDate(LocalDate.of(2024, 8, 28));
        paymentRequestforHos.setTypePayment("HOSPITALIZATION");
        paymentRequestforHos.setHospitalizationId("123");
        paymentRequestforHos.setStatus("SUCCESS");
        paymentRequestforHos.setTotalAmount(100.0);
        paymentRequestforHos.setPaymentMethod("VNPay");

        paymentRequestforAppointment = new PaymentRequest();
        paymentRequestforAppointment.setPaymentDate(LocalDate.of(2024, 8, 28));
        paymentRequestforAppointment.setTypePayment("APPOINTMENT");
        paymentRequestforAppointment.setHospitalizationId("456");
        paymentRequestforAppointment.setStatus("SUCCESS");
        paymentRequestforAppointment.setTotalAmount(100.0);
        paymentRequestforAppointment.setPaymentMethod("Cash");

        paymentResponse = PaymentResponse.builder()
                .id("1")
                .paymentDate(LocalDate.of(2024, 8, 28))
                .typePayment("HOSPITALIZATION")
                .paymentMethod("VNPay")
                .status("SUCCESS")
                .totalAmount(100.0)
                .build();

        invalidPaymentRequest = new PaymentRequest();
        invalidPaymentRequest.setPaymentDate(LocalDate.of(2024, 8, 28));
        invalidPaymentRequest.setTypePayment("Invalid Payment Type");
        invalidPaymentRequest.setHospitalizationId("123");


        refundRequest = new RefundRequest();
        refundRequest.setAmount(100);
        refundRequest.setAppointmentId("345");

        vnPayResponse = VNPayResponse.builder()
                .paymentUrl("http://vnpay")
                .code("00")
                .message("Successful")
                .build();

        dailyRevenue = Map.of(
                LocalDate.of(2024, 8, 28), 500.0,
                LocalDate.of(2024, 8, 29), 300.0
        );
    }


    @Test
    void cash() throws Exception {
        Mockito.when(paymentService.createCashPayment(Mockito.any(PaymentRequest.class)))
                .thenReturn(paymentResponse);

        mockMvc.perform(post("/v1/payments/cash")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequestforAppointment)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.typePayment").value("HOSPITALIZATION"))
                .andExpect(jsonPath("$.result.totalAmount").value(100.0))
                .andExpect(jsonPath("$.result.id").value("1"));
    }

    @Test
    void refund() throws Exception {
        Mockito.when(paymentService.refund(Mockito.any(RefundRequest.class)))
                .thenReturn(paymentResponse);

        mockMvc.perform(post("/v1/payments/refund")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(refundRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result.id").value("1"))
                .andExpect(jsonPath("$.result.paymentDate").value("2024-08-28"))
                .andExpect(jsonPath("$.result.status").value("SUCCESS"))
                .andExpect(jsonPath("$.result.paymentMethod").value("VNPay"));

    }

    @Test
    void getRevenueByDay() throws Exception {
        Mockito.when(paymentService.getRevenueByDay(Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
                .thenReturn(dailyRevenue);

        mockMvc.perform(get("/v1/payments/revenue/day")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .param("startDate", "2024-08-28")
                        .param("endDate", "2024-08-29"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result['2024-08-28']").value(500.0))
                .andExpect(jsonPath("$.result['2024-08-29']").value(300.0));
    }

    @Test
    void getRevenueByMonth() throws Exception {
        Map<Integer, Double> monthlyRevenue = Map.of(
                1, 1500.0,
                2, 1000.0
        );
        Mockito.when(paymentService.getRevenueByMonth(Mockito.anyInt()))
                .thenReturn(monthlyRevenue);

        mockMvc.perform(get("/v1/payments/revenue/month")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .param("year", "2024"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result['1']").value(1500.0))
                .andExpect(jsonPath("$.result['2']").value(1000.0));
    }

    @Test
    void getRevenueByYear() throws Exception {
        Map<Integer, Double> yearlyRevenue = Map.of(
                2023, 10000.0,
                2024, 20000.0
        );
        Mockito.when(paymentService.getRevenueByYear(Mockito.anyInt()))
                .thenReturn(yearlyRevenue);

        mockMvc.perform(get("/v1/payments/revenue/year")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .param("endYear", "2024"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result['2023']").value(10000.0))
                .andExpect(jsonPath("$.result['2024']").value(20000.0));
    }

    @Test
    void createVnPayPayment_NotFound() throws Exception {
        when(paymentService.createVnPayPayment(any(HttpServletRequest.class),any(PaymentRequest.class)))
                .thenThrow(new AppException(ErrorCode.NOT_FOUND));

        String requestJson = objectMapper.writeValueAsString(paymentRequestforAppointment);

        mockMvc.perform(post("/v1/payments/vnpay")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }
    @Test
    void payCallbackHandle_NotFound() throws Exception {

        doThrow(new AppException(ErrorCode.NOT_FOUND))
                .when(paymentService).payCallbackHandler(any(HttpServletRequest.class), any(HttpServletResponse.class), anyString());


        // Tạo đối tượng JSON cho yêu cầu
        String requestJson = objectMapper.writeValueAsString(paymentRequestforAppointment);

        // Thực hiện yêu cầu POST
        mockMvc.perform(post("/v1/payments/vnpay")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void createVnPayPayment_InvalidPaymentType() throws Exception {
        when(paymentService.createVnPayPayment(any(HttpServletRequest.class),any(PaymentRequest.class)))
                .thenThrow(new IllegalStateException("Unexpected value:" + invalidPaymentRequest.getTypePayment()));

        String requestJson = objectMapper.writeValueAsString(invalidPaymentRequest);

        mockMvc.perform(post("/v1/payments/vnpay")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCashPayment_NotFound() throws Exception {
        when(paymentService.createCashPayment(any(PaymentRequest.class)))
                .thenThrow(new AppException(ErrorCode.NOT_FOUND));

        String requestJson = objectMapper.writeValueAsString(paymentRequestforAppointment);

        mockMvc.perform(post("/v1/payments/cash")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void refund_NotFound() throws Exception {
        when(paymentService.refund(any(RefundRequest.class)))
                .thenThrow(new AppException(ErrorCode.NOT_FOUND));

        String requestJson = objectMapper.writeValueAsString(paymentRequestforAppointment);

        mockMvc.perform(post("/v1/payments/refund")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound());
    }

    @Test
    void refund_InvalidStatus() throws Exception {
        when(paymentService.refund(any(RefundRequest.class)))
                .thenThrow(new RuntimeException("This status invalid"));

        String requestJson = objectMapper.writeValueAsString(paymentRequestforAppointment);

        mockMvc.perform(post("/v1/payments/refund")
                        .header("Authorization", "Bearer " + getAuthToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("This status invalid"));
    }





    private String getAuthToken() throws Exception {
        String username = "customer@gmail.com";
        String password = "customerpass";

        String response = mockMvc.perform(post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return new ObjectMapper().readTree(response).get("result").get("token").asText();
    }
}
