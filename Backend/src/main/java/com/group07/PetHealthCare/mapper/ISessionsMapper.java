package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.SessionResponse;
import com.group07.PetHealthCare.pojo.Session;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISessionsMapper {
    SessionResponse toSessionResponse(Session session);
    Session toSession(Session session);
    List<SessionResponse> toSessionResponseList(List<Session> sessions);
}
