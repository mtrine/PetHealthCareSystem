package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.AppointmentCreationRequest;
import com.group07.PetHealthCare.pojo.Appointment;
import com.group07.PetHealthCare.respositytory.AppointmentRepository;
import com.group07.PetHealthCare.respositytory.PetRepository;
import com.group07.PetHealthCare.respositytory.SessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private SessionsRepository sessionsRepository;
    @Autowired
    private PetRepository petRepository;
    public Appointment createAppointment(AppointmentCreationRequest request) {
        Appointment appointment = new Appointment();
        appointment.setStatus(request.getStatus());
        appointment.setAppointmentDate(request.getAppointmentDate());
        return appointmentRepository.save(appointment);
    }
}
