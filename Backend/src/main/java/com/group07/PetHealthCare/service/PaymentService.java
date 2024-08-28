package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.config.VNPayConfig;
import com.group07.PetHealthCare.dto.request.PaymentRequest;
import com.group07.PetHealthCare.dto.response.PaymentResponse;
import com.group07.PetHealthCare.dto.response.VNPayResponse;
import com.group07.PetHealthCare.enumData.TYPEPAYMENT;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.pojo.*;
import com.group07.PetHealthCare.respositytory.*;
import com.group07.PetHealthCare.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service
@Slf4j
public class PaymentService {
    @Autowired
    private VNPayConfig vnPayConfig;
    @Autowired
    private IPaymentRepository paymentRepository;
    @Autowired
    private IPaymentForAppointmentRepository paymentForAppointmentRepository;
    @Autowired
    private IPaymentForHospitalizationRepository paymentForHospitalizationRepository;
    @Autowired
    private IHospitalizationRepository hospitalizationRepository;
    @Autowired
    private IAppointmentRepository appointmentRepository;
    private String paymentId;

    public VNPayResponse createVnPayPayment(HttpServletRequest request,@RequestBody PaymentRequest paymentRequest) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        Payment payment=switch(paymentRequest.getTypePayment()){
            case "APPOINTMENT" -> new PaymentForAppointment();
            case "HOSPITALIZATION" -> new PaymentForHospitalization();
            default -> throw new IllegalStateException("Unexpected value: " + paymentRequest.getTypePayment());
        };
        payment.setPaymentMethod("VNPay");
        payment.setPaymentDate(LocalDate.now());
        payment.setTotalAmount((double) amount);
        payment.setTypePayment(TYPEPAYMENT.valueOf(paymentRequest.getTypePayment()));
        if(payment instanceof PaymentForHospitalization){
            Hospitalization hospitalization= hospitalizationRepository.findById(paymentRequest.getHospitalizationId()).
                    orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
            ((PaymentForHospitalization) payment).setHospitalization(hospitalization);
           PaymentForHospitalization paymentForHospitalization= paymentForHospitalizationRepository.save((PaymentForHospitalization)payment);
           paymentId = paymentForHospitalization.getId();
        }
        else{
            Appointment appointment= appointmentRepository.findById(paymentRequest.getAppointmentId())
                            .orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
            ((PaymentForAppointment) payment).setAppointment(appointment);
            PaymentForAppointment paymentForAppointment= paymentForAppointmentRepository.save((PaymentForAppointment) payment);
            paymentId = paymentForAppointment.getId();
        }
        return VNPayResponse.builder()
                .code("00")
                .message("Successful")
                .paymentUrl(paymentUrl).build();
    }

    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse response, String port) throws IOException {
        String status = request.getParameter("vnp_ResponseCode");
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        // Tìm kiếm paymentForAppointment nếu tồn tại
        PaymentForAppointment paymentForAppointment = paymentForAppointmentRepository.findById(paymentId).orElse(null);

        if ("00".equals(status)) { // Nếu thanh toán thành công
            if (paymentForAppointment != null) {
                Appointment appointment = appointmentRepository.findById(paymentForAppointment.getAppointment().getId())
                        .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
                appointment.setStatus("Paid");
                appointment.setDeposit(payment.getTotalAmount());
                appointmentRepository.save(appointment); // Lưu thay đổi của appointment
            }
            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);
            response.sendRedirect("http://127.0.0.1:" + port + "/bookingSuccess.html");
        } else { // Nếu thanh toán thất bại
            if (paymentForAppointment != null) {
                Appointment appointment = paymentForAppointment.getAppointment();
                appointment.setStatus("FAILURE");
                appointmentRepository.save(appointment); // Lưu thay đổi của appointment
            }
            payment.setStatus("FAILURE");
            paymentRepository.save(payment); // Lưu thay đổi của payment
            response.sendRedirect("http://127.0.0.1:" + port + "/bookingFailed.html");
        }
    }


}
