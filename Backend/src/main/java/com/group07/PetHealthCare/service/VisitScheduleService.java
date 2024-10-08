package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.VisitScheduleRequest;
import com.group07.PetHealthCare.dto.response.VisitScheduleResponse;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.mapper.IVisitScheduleMapper;
import com.group07.PetHealthCare.pojo.*;
import com.group07.PetHealthCare.respositytory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitScheduleService {

    @Autowired
    private IVisitScheduleRepository visitScheduleRepository;

    @Autowired
    private IHospitalizationRepository hospitalizationRepository;

    @Autowired
    private IVeterinarianRepository veterinarianRepository;

    @Autowired
    private IAppointmentRepository appointmentRepository;

    @Autowired
    private ISessionsRepository sessionsRepository;

    @Autowired
    private IVisitScheduleMapper visitScheduleMapper;

    @PreAuthorize("hasAnyRole('STAFF')")
    public VisitScheduleResponse createVisitSchedule(VisitScheduleRequest request) {
        // Check if the visit schedule already exists
        boolean visitScheduleExists = visitScheduleRepository
                .findByVeterinarianIdAndVisitDateAndSessionId(
                        request.getVeterinarianId(), request.getVisitDate(), request.getSessionId()
                ).isPresent();

        // Check if an appointment already exists for the same time
        boolean appointmentExists = appointmentRepository
                .findByVeterinarianIdAndAppointmentDateAndSessionId(
                        request.getVeterinarianId(), request.getVisitDate(), request.getSessionId()
                ).isPresent();

        if (visitScheduleExists || appointmentExists) {
            throw new RuntimeException("Schedule not available.");
        }

        // Create a new visit schedule
        VisitSchedule visitSchedule = new VisitSchedule();
        visitSchedule.setVisitDate(request.getVisitDate());

        // Fetch and set Hospitalization, throw exception if not found
        Hospitalization hospitalization = hospitalizationRepository
                .findById(request.getHospitalizationId())
                .orElseThrow(() -> new RuntimeException("Hospitalization not found"));
        visitSchedule.setHospitalization(hospitalization);

        // Fetch and set Veterinarian, throw exception if not found
        Veterinarian veterinarian = veterinarianRepository
                .findById(request.getVeterinarianId())
                .orElseThrow(() -> new RuntimeException("Veterinarian not found"));
        visitSchedule.setVeterinarian(veterinarian);

        Session session= sessionsRepository.findById(request.getSessionId())
                .orElseThrow(() -> new RuntimeException("Session not found"));
        visitSchedule.setSession(session);
        // Save and return the visit schedule
        return visitScheduleMapper.toVisitScheduleResponse(visitScheduleRepository.save(visitSchedule)) ;
    }

    @PreAuthorize("hasAnyRole('VETERINARIAN')")
    public List<VisitScheduleResponse> getMyVisitSchedules() {
        SecurityContext context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        Veterinarian veterinarian=veterinarianRepository.findByEmail(email).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
        return visitScheduleMapper.toVisitScheduleResponses(visitScheduleRepository.findByVeterinarian(veterinarian));
    }

    @PreAuthorize("hasAnyRole('VETERINARIAN')")
    public VisitScheduleResponse updateVisitSchedule(String visitScheduleId,VisitScheduleRequest request) {
        VisitSchedule visitSchedule=visitScheduleRepository.findById(visitScheduleId).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
        if(request.getStatus()!=null){
            visitSchedule.setStatus(request.getStatus());
        }
        return visitScheduleMapper.toVisitScheduleResponse(visitScheduleRepository.save(visitSchedule));
    }
}

