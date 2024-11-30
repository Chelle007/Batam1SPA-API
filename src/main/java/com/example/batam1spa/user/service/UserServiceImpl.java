package com.example.batam1spa.user.service;

import com.example.batam1spa.user.model.User;
import com.example.batam1spa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void seedUser() {
        createUserIfNotExists("Desmond", "Admin", "Desmond123", "Desmond456");
        createUserIfNotExists("Jodie", "Owner", "Jodie123", "Jodie456");
        createUserIfNotExists("Michelle", "Manager", "Michelle123", "Michelle456");
        createUserIfNotExists("Vanness", "Admin", "Vanness123", "Vanness456");
    }

    private void createUserIfNotExists(String fullName, String managementLevel, String username, String password) {
        boolean userExists = userRepository.existsByUsername(username);

        if (userExists) {
            log.info("{} already exists in the system", username);
            return;
        }

        User user = User.builder()
                .fullName(fullName)
                .managementLevel(managementLevel)
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

        userRepository.save(user);
        log.info("{} has been added to the system", username);
    }
}
