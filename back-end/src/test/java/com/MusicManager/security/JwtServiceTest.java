package com.MusicManager.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        userDetails = new User("testuser", "password", new ArrayList<>());
    }

    @Test
    void generateTokenShouldCreateValidToken() {
        String token = jwtService.generateToken(userDetails);
        
        assertNotNull(token);
        assertTrue(token.length() > 0);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void extractUsernameShouldReturnCorrectUsername() {
        String token = jwtService.generateToken(userDetails);
        
        String username = jwtService.extractUsername(token);
        
        assertEquals("testuser", username);
    }

    @Test
    void isTokenValidShouldReturnFalseForInvalidUser() {
        String token = jwtService.generateToken(userDetails);
        UserDetails wrongUser = new User("wronguser", "password", new ArrayList<>());
        
        assertFalse(jwtService.isTokenValid(token, wrongUser));
    }
} 