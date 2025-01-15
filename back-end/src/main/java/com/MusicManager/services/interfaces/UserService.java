package com.MusicManager.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.MusicManager.dtos.UserDTO;
import com.MusicManager.dtos.UserRoleUpdateDTO;

public interface UserService {
    Page<UserDTO> listUsers(Pageable pageable);
    UserDTO updateUserRoles(String userId, UserRoleUpdateDTO roleUpdateDTO);
} 