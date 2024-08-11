package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.AppointmentRequest;
import com.group07.PetHealthCare.dto.response.AppointmentResponse;
import com.group07.PetHealthCare.pojo.*;
import com.group07.PetHealthCare.respositytory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private IAppointmentRepository IAppointmentRepository;
    @Autowired
    private IPetRepository IPetRepository;
    @Autowired
    private IServiceRepository IServiceRepository;
    @Autowired
    private IVeterinarianRepository IVeterinarianRepository;
    @Autowired
    private ISessionsRepository ISessionsRepository;
    @Autowired
    private IAppointmentServicesRepository IAppointmentServicesRepository;

    @Transactional
    public AppointmentRespones addAppointmentBySession(AppointmentRequest request) {
        // Kiểm tra ca làm việc hợp lệ
        Optional<Session> sessionOpt = ISessionsRepository.findById(request.getSessionId());
        if (sessionOpt.isEmpty()) {
            throw new RuntimeException("Session invalid");
        }

        // Tạo mới đối tượng Appointment
        Appointment appointment = new Appointment();
        Pet pet = IPetRepository.findById(request.getPetId()).orElseThrow(() -> new RuntimeException("Pet not found"));
        appointment.setPet(pet);
        appointment.setStatus(request.getStatus());
        appointment.setDescription(request.getDescription());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setDeposit(request.getDeposit());
        appointment.setSession(sessionOpt.get());

        // Lưu lịch hẹn
        Appointment savedAppointment = IAppointmentRepository.save(appointment);

        // Lưu các dịch vụ bổ sung
        for (String serviceId : request.getServiceId()) {
            Services service = IServiceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));
            AppointmentServices appointmentServices = new AppointmentServices();
            appointmentServices.setAppointment(savedAppointment);
            appointmentServices.setService(service);
            appointmentServices.setQuantity(1); // Đặt số lượng mặc định là 1, có thể điều chỉnh theo yêu cầu
            IAppointmentServicesRepository.save(appointmentServices);
        }

        return savedAppointment;
    }
    @Transactional

    public AppointmentResponse addAppointmentByVeterinarian(AppointmentRequest request) {
        // Kiểm tra bác sĩ có hợp lệ không
        Optional<Veterinarian> veterinarianOpt = IVeterinarianRepository.findById(request.getVeterinarianId());
        if (veterinarianOpt.isEmpty()) {
            throw new RuntimeException("Veterinarian not found");
        }

        // Kiểm tra ca làm việc hợp lệ
        Optional<Session> sessionOpt = ISessionsRepository.findById(request.getSessionId());
        if (sessionOpt.isEmpty()) {
            throw new RuntimeException("Session not found");
        }

        // Kiểm tra bác sĩ có lịch trống cho ca này không
        Optional<Appointment> appointmentOpt = IAppointmentRepository.findByVeterinarianIdAndAppointmentDateAndSessionId(request.getVeterinarianId(), request.getAppointmentDate(), request.getSessionId());
        if (appointmentOpt.isPresent()) {
            throw new RuntimeException("Veterinarian's session not available");
        }

        // Tạo mới đối tượng Appointment
        Appointment appointment = new Appointment();
        Pet pet = IPetRepository.findById(request.getPetId()).orElseThrow(() -> new RuntimeException("Pet not found"));
        appointment.setPet(pet);
        appointment.setStatus(request.getStatus());
        appointment.setDescription(request.getDescription());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setDeposit(request.getDeposit());

        // Gán bác sĩ và ca làm việc cho lịch hẹn
        appointment.setVeterinarian(veterinarianOpt.get());
        appointment.setSession(sessionOpt.get());

        // Lưu lịch hẹn
        Appointment savedAppointment = IAppointmentRepository.save(appointment);

        // Lưu các dịch vụ bổ sung
        for (String serviceId : request.getServiceId()) {
            System.out.print("Service Id: " + serviceId);
            Services service = IServiceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));
            AppointmentServices appointmentServices = new AppointmentServices();
            appointmentServices.setAppointment(savedAppointment);
            appointmentServices.setService(service);
            appointmentServices.setQuantity(1); // Đặt số lượng mặc định là 1, có thể điều chỉnh theo yêu cầu

            IAppointmentServicesRepository.save(appointmentServices);
        }

        return savedAppointment;
    }
    @Transactional
    public List<AppointmentResponse> getAllAppointments() {
        return IAppointmentRepository.findAll();
    }
    @Transactional
    public List<AppointmentResponse> getAppointmentByVeterinarianId(String veterinarianId) {
        return IAppointmentRepository.findByVeterinarianId(veterinarianId);
    }

    @Transactional
    public AppointmentResponse changeInforAppointment(String appointmentId, AppointmentRequest request) {
        Appointment appointment = IAppointmentRepository.findById(appointmentId).orElseThrow(() -> new RuntimeException("Appointment not found"));
        if (request.getStatus() != null) {
            appointment.setStatus(request.getStatus());
        }
        if (request.getDescription() != null) {
            appointment.setDescription(request.getDescription());
        }
        if (request.getAppointmentDate() != null) {
            appointment.setAppointmentDate(request.getAppointmentDate());
        }
        if (request.getDeposit() != null) {
            appointment.setDeposit(request.getDeposit());
        }

        // Cập nhật thông tin các thực thể liên quan nếu có ID trong request
        if (request.getPetId() != null) {
            Pet pet = IPetRepository.findById(request.getPetId()).orElseThrow(() -> new RuntimeException("Pet not found"));
            appointment.setPet(pet);
        }

        if (request.getServiceId() != null) {
            // Xóa các dịch vụ cũ
            IAppointmentServicesRepository.deleteByAppointmentId(appointmentId);

            // Thêm các dịch vụ mới
            for (String serviceId : request.getServiceId()) {
                System.out.print("Service Id: " + serviceId);
                Services service = IServiceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service not found"));
                AppointmentServices appointmentServices = new AppointmentServices();
                appointmentServices.setAppointment(appointment);
                appointmentServices.setService(service);
                appointmentServices.setQuantity(1); // Đặt số lượng mặc định là 1, có thể điều chỉnh theo yêu cầu

                IAppointmentServicesRepository.save(appointmentServices);
            }
        }

        return IAppointmentRepository.save(appointment);
    }
}

