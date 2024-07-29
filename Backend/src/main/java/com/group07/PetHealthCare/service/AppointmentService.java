package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.AppointmentRequest;
import com.group07.PetHealthCare.pojo.*;
import com.group07.PetHealthCare.respositytory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private VeterinarianscheduleRepository veterinarianscheduleRepository;

    public Appointment addAppointment(AppointmentRequest request) {
        Appointment appointment = new Appointment();

        // Fetch the necessary entities
        Pet pet = petRepository.findById(request.getPetId()).orElseThrow(() -> new RuntimeException("Pet not found"));
        Services service = serviceRepository.findById(request.getServiceId()).orElseThrow(() -> new RuntimeException("Service not found"));
        Veterinarianschedule veterinarianschedule=veterinarianscheduleRepository.findById(request.getVeterinarianScheduleId()).orElseThrow(() -> new RuntimeException("Veterinarian Schedule not found"));
        if(!Objects.equals(veterinarianschedule.getStatus(), "Empty")){
            throw new RuntimeException("The schedule has been set");
        }
        appointment.setPet(pet);
        appointment.setService(service);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setDescription(request.getDescription());
        appointment.setVeterinarianschedule(veterinarianschedule);
        veterinarianschedule.setStatus("Scheduled");
        // Save the appointment to the database
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
