package com.MusicManager.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.MusicManager.dtos.UserDTO;
import com.MusicManager.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(role -> role.getName()).collect(java.util.stream.Collectors.toList()))")
    UserDTO userToUserDTO(User user);
} 