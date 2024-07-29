package com.group07.PetHealthCare.service;

import com.group07.PetHealthCare.dto.request.SessionsRequest;
import com.group07.PetHealthCare.pojo.Session;
import com.group07.PetHealthCare.respositytory.SessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionsService {
    @Autowired
    private SessionsRepository sessionsRepository;

    public Session createSession(SessionsRequest request) {
        Session session = new Session();
        session.setStartTime(request.getStartTime());
        session.setEndTime(request.getEndTime());

        return sessionsRepository.save(session);

    }
}
