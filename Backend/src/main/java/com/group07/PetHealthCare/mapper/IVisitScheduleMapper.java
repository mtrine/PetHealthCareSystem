package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.VisitScheduleResponse;
import com.group07.PetHealthCare.pojo.VisitSchedule;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IVisitScheduleMapper {
    VisitScheduleResponse toVisitScheduleResponse(VisitSchedule visitSchedule);
    VisitSchedule toVisitSchedule(VisitScheduleResponse visitScheduleResponse);
    List<VisitScheduleResponse> toVisitScheduleResponses(List<VisitSchedule> visitSchedules);
}
