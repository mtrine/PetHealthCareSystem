package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.VeterinarianScheduleRequest;
import com.group07.PetHealthCare.pojo.Session;
import com.group07.PetHealthCare.pojo.Veterinarian;
import com.group07.PetHealthCare.pojo.Veterinarianschedule;
import com.group07.PetHealthCare.respositytory.ISessionsRepository;
import com.group07.PetHealthCare.respositytory.IVeterinarianRepository;
import com.group07.PetHealthCare.respositytory.IVeterinarianscheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeterinarianScheduleService {
    @Autowired
    IVeterinarianscheduleRepository IVeterinarianscheduleRepository;

    @Autowired
    IVeterinarianRepository IVeterinarianRepository;

    @Autowired
    ISessionsRepository ISessionsRepository;


    public Veterinarianschedule addSchedule(VeterinarianScheduleRequest request) {
        // Retrieve related entities
        Veterinarian veterinarian = IVeterinarianRepository.findById(request.getVeterinarianId())
                .orElseThrow(() -> new RuntimeException("Veterinarian not found"));
        Session session = ISessionsRepository.findById(request.getSessionId())
                .orElseThrow(() -> new RuntimeException("Session not found"));

        // Create new Veterinarianschedule instance
        Veterinarianschedule veterinarianschedule = new Veterinarianschedule();

        // Set required fields
        veterinarianschedule.setSession(session);
        veterinarianschedule.setVeterinarian(veterinarian);
        veterinarianschedule.setDayOfWeek(request.getDayOfWeek());

        // Set default values if needed
        veterinarianschedule.setStatus("Empty"); // Set default status
        veterinarianschedule.setIsPublished(false); // Set default publication status

        // Save and return the new schedule
        return IVeterinarianscheduleRepository.save(veterinarianschedule);
    }

}
