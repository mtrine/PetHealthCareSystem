package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.config.VNPayConfig;
import com.group07.PetHealthCare.dto.request.PaymentRequest;
import com.group07.PetHealthCare.dto.request.RefundRequest;
import com.group07.PetHealthCare.dto.response.PaymentResponse;
import com.group07.PetHealthCare.dto.response.VNPayResponse;
import com.group07.PetHealthCare.enumData.TYPEPAYMENT;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.mapper.IPaymentMapper;
import com.group07.PetHealthCare.pojo.*;
import com.group07.PetHealthCare.respositytory.*;
import com.group07.PetHealthCare.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IPaymentMapper paymentMapper;

    private String paymentId;
    private long newAmount=0;
    private String role="";
    public VNPayResponse createVnPayPayment(HttpServletRequest request,@RequestBody PaymentRequest paymentRequest) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        SecurityContext context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user=userRepository.findByEmail(name).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
        role= String.valueOf(user.getRole());
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
        Payment existPayment=paymentForAppointmentRepository.findByAppointmentId(paymentRequest.getAppointmentId()).orElse(null);
        if(existPayment==null) {
            Payment payment=switch(paymentRequest.getTypePayment()){
                case "APPOINTMENT" -> new PaymentForAppointment();
                case "HOSPITALIZATION" -> new PaymentForHospitalization();
                default -> throw new IllegalStateException("Unexpected value: " + paymentRequest.getTypePayment());
            };
            payment.setPaymentMethod("VNPay");
            payment.setPaymentDate(LocalDate.now());
            payment.setTotalAmount((double) amount/100);
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
        }
        else{
            if(amount>0){
                newAmount=amount/100;
                paymentId = existPayment.getId();
            }
        }
        return VNPayResponse.builder()
                .code("00")
                .message("Successful")
                .paymentUrl(paymentUrl).build();
    }

    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse response,String baseUrl) throws IOException {
        String status = request.getParameter("vnp_ResponseCode");
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        // Tìm kiếm paymentForAppointment nếu tồn tại
        PaymentForAppointment paymentForAppointment = paymentForAppointmentRepository.findById(paymentId).orElse(null);

        if ("00".equals(status)) { // Nếu thanh toán thành công
            if (paymentForAppointment != null) {
                if(newAmount!=0){
                    paymentForAppointment.setTotalAmount(paymentForAppointment.getTotalAmount()+newAmount);
                    Appointment appointment =paymentForAppointment.getAppointment();
                    appointment.setStatus("Success");
                    appointmentRepository.save(appointment);
                    paymentRepository.save(paymentForAppointment);
                }
                else{
                    Appointment appointment = appointmentRepository.findById(paymentForAppointment.getAppointment().getId())
                            .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
                    appointment.setStatus("Paid");
                    appointment.setDeposit(payment.getTotalAmount());
                    appointmentRepository.save(appointment); // Lưu thay đổi của appointment
                }
            }
            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);
            if(Objects.equals(role, "CUSTOMER")){
                response.sendRedirect(baseUrl+ "/bookingSuccess.html");
            }
            else{
                response.sendRedirect(baseUrl + "/Staff/bookingSuccessForStaff.html");
            }
        } else { // Nếu thanh toán thất bại
            if (paymentForAppointment != null) {
                Appointment appointment = paymentForAppointment.getAppointment();
                appointment.setStatus("FAILURE");
                appointmentRepository.save(appointment); // Lưu thay đổi của appointment
            }
            payment.setStatus("FAILURE");
            paymentRepository.save(payment); // Lưu thay đổi của payment
            if(Objects.equals(role, "CUSTOMER")){
                response.sendRedirect( baseUrl + "/bookingFailed.html");
            }
            else{
                response.sendRedirect( baseUrl + "/Staff/bookingFailedForStaff.html");
            }
        }
    }

    public PaymentResponse createCashPayment(PaymentRequest paymentRequest) {
        Payment existPayment=paymentForAppointmentRepository.findByAppointmentId(paymentRequest.getAppointmentId()).orElse(null);
        if(existPayment==null) {
            Payment payment = switch (paymentRequest.getTypePayment()) {
                case "APPOINTMENT" -> new PaymentForAppointment();
                case "HOSPITALIZATION" -> new PaymentForHospitalization();
                default -> throw new IllegalStateException("Unexpected value: " + paymentRequest.getTypePayment());
            };
            payment.setPaymentMethod("Cash");
            payment.setPaymentDate(LocalDate.now());
            payment.setTotalAmount(paymentRequest.getTotalAmount());
            payment.setStatus("SUCCESS");
            payment.setTypePayment(TYPEPAYMENT.valueOf(paymentRequest.getTypePayment()));
            if (payment instanceof PaymentForHospitalization) {
                Hospitalization hospitalization = hospitalizationRepository.findById(paymentRequest.getHospitalizationId()).
                        orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
                ((PaymentForHospitalization) payment).setHospitalization(hospitalization);
                return paymentMapper.mapResponse(paymentForHospitalizationRepository.save((PaymentForHospitalization) payment));

            } else {
                Appointment appointment = appointmentRepository.findById(paymentRequest.getAppointmentId())
                        .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
                appointment.setStatus("Paid");
                appointmentRepository.save(appointment);
                ((PaymentForAppointment) payment).setAppointment(appointment);
                return paymentMapper.mapResponse(paymentForAppointmentRepository.save((PaymentForAppointment) payment));

            }

        }else{
            existPayment.setTotalAmount(existPayment.getTotalAmount()+paymentRequest.getTotalAmount());
            Appointment appointment= appointmentRepository.findById(paymentRequest.getAppointmentId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
            appointment.setStatus("Success");
            existPayment.setStatus("SUCCESS");
            appointmentRepository.save(appointment);
            return paymentMapper.mapResponse(paymentRepository.save(existPayment));
        }
    }

    public PaymentResponse refund(RefundRequest refundRequest) {
        PaymentForAppointment existPayment=paymentForAppointmentRepository.findByAppointmentId(refundRequest.getAppointmentId()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        Appointment appointment=existPayment.getAppointment();
        LocalDate today=LocalDate.now();
        LocalDate paymentDate = existPayment.getPaymentDate();
        long numberOfDays = ChronoUnit.DAYS.between(paymentDate, today);
        if(Objects.equals(appointment.getStatus(), "Paid")) {
            if(numberOfDays<=6&&numberOfDays>=3){
                existPayment.setTotalAmount(existPayment.getTotalAmount()*0.25);
            } else if (numberOfDays<=2) {
                existPayment.setTotalAmount(0.0);
            }
            appointment.setStatus("Cancelled");
            appointmentRepository.save(appointment);
        }
        else{
            throw new RuntimeException("This status invalid");
        }
        return paymentMapper.mapResponse(paymentRepository.save(existPayment));
    }


    public Map<LocalDate, Double> getRevenueByDay(LocalDate startDate, LocalDate endDate) {
        List<Payment> payments = paymentRepository.findAllByPaymentDateBetween(startDate, endDate);
        return payments.stream()
                .filter(payment -> "SUCCESS".equals(payment.getStatus())) // Lọc thanh toán có trạng thái SUCCESS
                .collect(Collectors.groupingBy(
                        Payment::getPaymentDate,
                        Collectors.summingDouble(Payment::getTotalAmount)
                ));
    }


    public Map<Integer, Double> getRevenueByMonth(int year) {
        List<Payment> payments = paymentRepository.findAllByPaymentDateYear(year);

        return payments.stream()
                .filter(payment -> "SUCCESS".equals(payment.getStatus())) // Lọc thanh toán có trạng thái SUCCESS
                .collect(Collectors.groupingBy(
                        payment -> payment.getPaymentDate().getMonthValue(), // Chuyển tháng thành số (1-12)
                        Collectors.summingDouble(Payment::getTotalAmount)
                ));
    }



    public Map<Integer, Double> getRevenueByYear(int endYear) {
        int startYear = 2000;
        List<Payment> payments = paymentRepository.findAllByPaymentDateYearBetween(startYear, endYear);
        return payments.stream()
                .filter(payment -> "SUCCESS".equals(payment.getStatus())) // Lọc thanh toán có trạng thái SUCCESS
                .collect(Collectors.groupingBy(
                        payment -> payment.getPaymentDate().getYear(),
                        Collectors.summingDouble(Payment::getTotalAmount)
                ));
    }

}
