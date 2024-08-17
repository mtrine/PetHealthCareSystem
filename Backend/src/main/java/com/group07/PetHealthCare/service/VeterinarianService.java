package com.group07.PetHealthCare.service;


import com.group07.PetHealthCare.dto.response.SessionResponse;
import com.group07.PetHealthCare.dto.response.VeterinarianResponse;
import com.group07.PetHealthCare.exception.AppException;
import com.group07.PetHealthCare.exception.ErrorCode;
import com.group07.PetHealthCare.mapper.ISessionsMapper;
import com.group07.PetHealthCare.mapper.IVeterinarianMapper;
import com.group07.PetHealthCare.pojo.Appointment;
import com.group07.PetHealthCare.pojo.Session;
import com.group07.PetHealthCare.pojo.Veterinarian;
import com.group07.PetHealthCare.respositytory.IAppointmentRepository;
import com.group07.PetHealthCare.respositytory.ISessionsRepository;
import com.group07.PetHealthCare.respositytory.IVeterinarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VeterinarianService {
    @Autowired
    private IVeterinarianRepository IVeterinarianRepository;
    @Autowired
    private ISessionsRepository ISessionsRepository;
    @Autowired
    private IAppointmentRepository IAppointmentRepository;
    @Autowired
    private IVeterinarianMapper veterinarianMapper;
    @Autowired
    private ISessionsMapper sessionsMapper;
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public List<VeterinarianResponse> getAllVeterinarian() {
        return veterinarianMapper.toResponseList(IVeterinarianRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public VeterinarianResponse getVeterinarianById (String Id)
    {
        Optional<Veterinarian> veterinarian = IVeterinarianRepository.findById(Id);
        if(veterinarian.isEmpty()){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        return veterinarianMapper.toResponse(veterinarian.get());
    }


    public List<SessionResponse> getAvailableSessionsForVeterinarian(String veterinarianId, LocalDate appointmentDate) {
        // Lấy tất cả các ca làm việc
        List<Session> allSessions = ISessionsRepository.findAll();

        // Lấy tất cả các lịch hẹn của bác sĩ trong ngày đó
        List<Appointment> appointments = IAppointmentRepository.findByVeterinarianIdAndAppointmentDate(veterinarianId, appointmentDate);

        // Loại bỏ các ca đã có lịch hẹn
        for (Appointment appointment : appointments) {
            allSessions.removeIf(session -> session.getId()==appointment.getSession().getId());
        }

        return sessionsMapper.toSessionResponseList(allSessions);
    }
}
