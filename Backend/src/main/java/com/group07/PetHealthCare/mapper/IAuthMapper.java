package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.response.AuthResponse;
import com.group07.PetHealthCare.pojo.User;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface IAuthMapper {
    AuthResponse toAuthRespone(User user);
}
