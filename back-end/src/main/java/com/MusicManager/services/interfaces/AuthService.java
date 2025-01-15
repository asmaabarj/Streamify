package com.MusicManager.services.interfaces;

import com.MusicManager.dtos.auth.AuthResponse;
import com.MusicManager.dtos.auth.LoginRequest;
import com.MusicManager.dtos.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}