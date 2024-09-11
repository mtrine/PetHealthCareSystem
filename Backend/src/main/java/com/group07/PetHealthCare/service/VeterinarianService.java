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
import com.group07.PetHealthCare.pojo.VisitSchedule;
import com.group07.PetHealthCare.respositytory.IAppointmentRepository;
import com.group07.PetHealthCare.respositytory.ISessionsRepository;
import com.group07.PetHealthCare.respositytory.IVeterinarianRepository;
import com.group07.PetHealthCare.respositytory.IVisitScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @Autowired
    private IVisitScheduleRepository visitScheduleRepository;
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','CUSTOMER')")
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
        // Lấy tất cả các session của bác sĩ trong ngày đó
        List<Session> allSessions = ISessionsRepository.findAll();

        // Lấy tất cả các lịch hẹn của bác sĩ trong ngày đó
        List<Appointment> appointments = IAppointmentRepository.findByVeterinarianIdAndAppointmentDate(veterinarianId, appointmentDate);

        // Loại bỏ các session đã có lịch hẹn với status = Paid hoặc đã được lên lịch trong VisitSchedule với isPublished = false
        allSessions.removeIf(session ->
                // Kiểm tra nếu có Appointment với status = "Paid"
                appointments.stream().anyMatch(appointment ->
                        appointment.getSession().getId() == session.getId() && "Paid".equals(appointment.getStatus())) ||
                        visitScheduleRepository.findByVeterinarianIdAndVisitDateAndSessionId(veterinarianId, appointmentDate, session.getId())
                                .map(visitSchedule -> !visitSchedule.isStatus())
                                .orElse(false)
        );

        return sessionsMapper.toSessionResponseList(allSessions);
    }



    public List<VeterinarianResponse> getAvailableVeterinariansForSessionAndDate(int sessionId, LocalDate appointmentDate) {
        // Lấy tất cả các bác sĩ
        List<Veterinarian> allVeterinarians = IVeterinarianRepository.findAll();

        // Lọc những bác sĩ không có lịch hẹn trong ca làm việc đó và ngày đó
        List<Veterinarian> availableVeterinarians = new ArrayList<>();

        for (Veterinarian veterinarian : allVeterinarians) {
            // Lấy danh sách các cuộc hẹn của bác sĩ trong ngày đó và ca đó
            List<Appointment> appointments = IAppointmentRepository.findByVeterinarianIdAndAppointmentDate(veterinarian.getId(), appointmentDate);

            // Kiểm tra xem bác sĩ có rảnh trong ca làm việc đó không
            boolean isAvailable = appointments.stream().noneMatch(appointment ->
                    appointment.getSession().getId() == sessionId && "Paid".equals(appointment.getStatus())  // Chỉ loại bỏ nếu status là "Paid"
            );

            if (isAvailable) {
                // Kiểm tra nếu không có VisitSchedule trùng với ca làm việc và ngày đó hoặc VisitSchedule đã được published
                Optional<VisitSchedule> visitSchedule = visitScheduleRepository.findByVeterinarianIdAndVisitDateAndSessionId(veterinarian.getId(), appointmentDate, sessionId);

                if (visitSchedule.isEmpty() || visitSchedule.get().isStatus()) {  // Nếu không có hoặc isPublished = true thì bác sĩ vẫn rảnh
                    availableVeterinarians.add(veterinarian);
                }
            }
        }

        // Chuyển đổi danh sách bác sĩ rảnh sang dạng response và trả về
        return veterinarianMapper.toResponseList(availableVeterinarians);
    }

@PreAuthorize("hasRole('VETERINARIAN')")
    public VeterinarianResponse getMyInfo(){
        SecurityContext context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();
        Veterinarian veterinarian=IVeterinarianRepository.findByEmail(email).orElseThrow(()->new AppException(ErrorCode.NOT_FOUND));
        return veterinarianMapper.toResponse(veterinarian);
    }

}
