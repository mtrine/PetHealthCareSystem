package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.VisitScheduleResponse;
import com.group07.PetHealthCare.pojo.VisitSchedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",uses={ISessionsMapper.class,IVeterinarianMapper.class,IHospitalizationMapper.class})
public interface IVisitScheduleMapper {
    @Mapping(source="veterinarian",target="veterinarianResponse")
    @Mapping(source="hospitalization",target="hospitalizationResponse")
    @Mapping(source="session",target="sessionResponse")
    VisitScheduleResponse toVisitScheduleResponse(VisitSchedule visitSchedule);
    VisitSchedule toVisitSchedule(VisitScheduleResponse visitScheduleResponse);
    List<VisitScheduleResponse> toVisitScheduleResponses(List<VisitSchedule> visitSchedules);
}
