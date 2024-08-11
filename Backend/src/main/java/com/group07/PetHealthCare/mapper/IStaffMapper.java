package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.StaffResponse;
import com.group07.PetHealthCare.pojo.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IStaffMapper {
    @Mapping(source = "isAdmin", target = "isAdmin")
    StaffResponse toRespone(Staff staff);
    @Mapping(source = "isAdmin", target = "isAdmin")
    List<StaffResponse> toResponeList(List<Staff> staffList);
}
