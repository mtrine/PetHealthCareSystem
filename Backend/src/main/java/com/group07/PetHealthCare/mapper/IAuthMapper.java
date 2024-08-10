package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.respone.AuthRespone;
import com.group07.PetHealthCare.pojo.User;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface IAuthMapper {
    AuthRespone toAuthRespone(User user);
}
