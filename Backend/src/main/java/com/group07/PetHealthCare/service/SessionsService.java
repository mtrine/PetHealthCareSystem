package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.SessionsRequest;
import com.group07.PetHealthCare.dto.response.SessionResponse;
import com.group07.PetHealthCare.mapper.ISessionsMapper;
import com.group07.PetHealthCare.pojo.Session;
import com.group07.PetHealthCare.respositytory.ISessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionsService {
    @Autowired
    private ISessionsRepository ISessionsRepository;
    @Autowired
    private ISessionsMapper sessionsMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public SessionResponse createSession(SessionsRequest request) {
        Session session = new Session();
        if(request.getStartTime().isAfter(request.getEndTime())){
            throw new RuntimeException("Start time must be before end time");
        }
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());

        return sessionsMapper.toSessionResponse(ISessionsRepository.save(session));

    }

    public List<SessionResponse> getAllSession(){

        return sessionsMapper.toSessionResponseList(ISessionsRepository.findAll());
    }
}
