package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.AdminResponse;
import com.group07.PetHealthCare.pojo.Admin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAdminMapper {
    AdminResponse toAdminResponse(Admin admin);
    Admin toAdmin(AdminResponse adminResponse);
    List<AdminResponse> toAdminResponseList(List<Admin> admins);
}
