package com.MusicManager.controllers.admins;

import com.MusicManager.dtos.UserDTO;
import com.MusicManager.dtos.UserRoleUpdateDTO;
import com.MusicManager.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UsersController {
    
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(userService.listUsers(pageable));
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<UserDTO> updateUserRoles(
            @PathVariable String id,
            @Valid @RequestBody UserRoleUpdateDTO roleUpdateDTO) {
        return ResponseEntity.ok(userService.updateUserRoles(id, roleUpdateDTO));
    }
}
