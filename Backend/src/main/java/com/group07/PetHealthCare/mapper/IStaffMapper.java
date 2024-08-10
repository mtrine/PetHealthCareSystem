package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.respone.StaffRespone;
import com.group07.PetHealthCare.pojo.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IStaffMapper {
    @Mapping(source = "isAdmin", target = "isAdmin")
    StaffRespone toRespone(Staff staff);
    @Mapping(source = "isAdmin", target = "isAdmin")
    List<StaffRespone> toResponeList(List<Staff> staffList);
}
