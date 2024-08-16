package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.StaffResponse;
import com.group07.PetHealthCare.pojo.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IStaffMapper {
    StaffResponse toRespone(Staff staff);
    List<StaffResponse> toResponeList(List<Staff> staffList);
    Staff toStaff(StaffResponse staffResponse);
}
