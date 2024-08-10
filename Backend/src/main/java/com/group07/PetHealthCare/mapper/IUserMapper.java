package com.group07.PetHealthCare.mapper;

import com.group07.PetHealthCare.dto.request.UserRequest;
import com.group07.PetHealthCare.dto.respone.UserRespone;
import com.group07.PetHealthCare.pojo.User;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface IUserMapper {
    UserRespone toUserRespone(User user);
}
