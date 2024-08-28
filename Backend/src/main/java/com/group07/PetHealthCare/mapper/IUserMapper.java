package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.dto.response.UserResponse;
import com.group07.PetHealthCare.pojo.User;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface IUserMapper {
    UserResponse toUserRespone(User user);
}
