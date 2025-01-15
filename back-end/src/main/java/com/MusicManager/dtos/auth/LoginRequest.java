package com.MusicManager.dtos.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String password;
}