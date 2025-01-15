package com.MusicManager.dtos;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserRoleUpdateDTO {
    @NotEmpty(message = "La liste des rôles ne peut pas être vide")
    private List<String> roleNames;
} 