package com.example.batam1spa.auth.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.batam1spa.auth.dto.AuthResponse;
import com.example.batam1spa.auth.dto.LoginRequest;
import com.example.batam1spa.auth.exception.AuthException;
import com.example.batam1spa.security.service.JwtService;
import com.example.batam1spa.user.model.User;
import com.example.batam1spa.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AuthResponse authenticate(LoginRequest request) {
        User user = findUserByUsername(request.getUsername());
        validatePassword(request.getPassword(), user.getPassword());

        String token = generateToken(user);

        return AuthResponse.of(user, token);
    }

    private User findUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User with username {} not found", username);
                    return new AuthenticationException("User not found");
                });
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            log.error("Invalid password");
            throw new AuthenticationException("Invalid password");
        }
    }

    private String generateToken(User user) {
        return jwtService.generateToken(user);
    }
}
