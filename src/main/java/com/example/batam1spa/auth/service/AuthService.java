package com.example.batam1spa.auth.service;

import com.example.batam1spa.auth.dto.AuthResponse;
import com.example.batam1spa.auth.dto.LoginRequest;

public interface AuthService {

    AuthResponse authenticate(LoginRequest request);

}