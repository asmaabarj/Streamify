package com.MusicManager.services.impl;

import com.MusicManager.dtos.UserDTO;
import com.MusicManager.dtos.UserRoleUpdateDTO;
import com.MusicManager.exceptions.UserException;
import com.MusicManager.mappers.UserMapper;
import com.MusicManager.model.Role;
import com.MusicManager.model.User;
import com.MusicManager.repositories.RoleRepository;
import com.MusicManager.repositories.UserRepository;
import com.MusicManager.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public Page<UserDTO> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::userToUserDTO);
    }

    @Override
    public UserDTO updateUserRoles(String userId, UserRoleUpdateDTO roleUpdateDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("Utilisateur non trouvé"));

        List<Role> newRoles = roleUpdateDTO.getRoleNames().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new UserException("Rôle non trouvé: " + roleName)))
                .collect(Collectors.toList());

        user.setRoles(newRoles);
        User updatedUser = userRepository.save(user);
        return userMapper.userToUserDTO(updatedUser);
    }
} 