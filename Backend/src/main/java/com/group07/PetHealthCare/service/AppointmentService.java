package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.AppointmentRequest;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.pojo.*;
import com.group07.PetHealthCare.respositytory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public Appointment addAppointmentBySession(AppointmentRequest request) {
        // Kiểm tra xem ca có hợp lệ không
        Optional<Session> sessionOpt = ISessionsRepository.findById(request.getSessionId());
        if (sessionOpt.isEmpty()) {
            throw new RuntimeException("Session invalid");
        }

        // Tạo mới đối tượng Appointment mà không gán bác sĩ
        Appointment appointment = new Appointment();
        Pet pet = IPetRepository.findById(request.getPetId()).orElseThrow(()->new RuntimeException("Pet not found"));
        Services services = IServiceRepository.findById(request.getServiceId()).orElseThrow(()->new RuntimeException("Service not found"));
        appointment.setPet(pet);
        appointment.setService(services);
        appointment.setStatus(request.getStatus());
        appointment.setDescription(request.getDescription());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setDeposit(request.getDeposit());
        appointment.setSession(sessionOpt.get());

        return IAppointmentRepository.save(appointment);
    }

    public Appointment addAppointmentByVeterinarian(AppointmentRequest request) {
        // Kiểm tra bác sĩ có hợp lệ không
        Optional<Veterinarian> veterinarianOpt = IVeterinarianRepository.findById(request.getVeterinarianId());
        if (veterinarianOpt.isEmpty()) {
            throw new RuntimeException("Veterinarian not found");
        }

        // Kiểm tra ca làm việc có hợp lệ không
        Optional<Session> sessionOpt = ISessionsRepository.findById(request.getSessionId());
        if (sessionOpt.isEmpty()) {
            throw new RuntimeException("Sessions not found");
        }

        // Kiểm tra bác sĩ có lịch trống cho ca này không
        Optional<Appointment> appointmentOpt= IAppointmentRepository.findByVeterinarianIdAndAppointmentDateAndSessionId(request.getVeterinarianId(),request.getAppointmentDate(), request.getSessionId());
        if (appointmentOpt.isPresent()) {
            throw new RuntimeException("Veterinarian's session not available");
        }

        // Tạo mới đối tượng Appointment
        Appointment appointment = new Appointment();
        Pet pet = IPetRepository.findById(request.getPetId()).orElseThrow(()->new RuntimeException("Pet not found"));
        Services services = IServiceRepository.findById(request.getServiceId()).orElseThrow(()->new RuntimeException("Service not found"));
        appointment.setPet(pet);
        appointment.setService(services);
        appointment.setStatus(request.getStatus());
        appointment.setDescription(request.getDescription());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setDeposit(request.getDeposit());

        // Gán bác sĩ và ca làm việc cho lịch hẹn
        appointment.setVeterinarian(veterinarianOpt.get());
        appointment.setSession(sessionOpt.get());

        return IAppointmentRepository.save(appointment);
    }
    public Appointment assignVeterinarianToAppointment(String appointmentId, String veterinarianId) {
        // Kiểm tra lịch hẹn có hợp lệ không
        Optional<Appointment> appointmentOpt = IAppointmentRepository.findById(appointmentId);
        if (appointmentOpt.isEmpty()) {
            throw new RuntimeException("Appointment Invalid");
        }

        // Kiểm tra bác sĩ có hợp lệ không
        Optional<Veterinarian> veterinarianOpt = IVeterinarianRepository.findById(veterinarianId);
        if (veterinarianOpt.isEmpty()) {
            throw new RuntimeException("Veterinarian not found");
        }

        // Cập nhật bác sĩ cho lịch hẹn
        Appointment appointment = appointmentOpt.get();
        appointment.setVeterinarian(veterinarianOpt.get());

        return IAppointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return IAppointmentRepository.findAll();
    }

}

