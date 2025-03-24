package com.example.batam1spa.user.service;

import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.user.dto.CreateUserRequest;
import com.example.batam1spa.user.dto.GetUserPaginationResponse;
import com.example.batam1spa.user.dto.GetUserResponse;
import com.example.batam1spa.user.exception.UserExceptions;
import com.example.batam1spa.user.model.User;
import com.example.batam1spa.user.model.UserRole;
import com.example.batam1spa.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final RoleSecurityService roleSecurityService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public void seedUser() {
        createUserIfNotExists("Desmond", UserRole.ADMIN, "Desmond123", "Desmond456");
        createUserIfNotExists("Jodie", UserRole.OWNER, "Jodie123", "Jodie456");
        createUserIfNotExists("Michelle", UserRole.MANAGER, "Michelle123", "Michelle456");
        createUserIfNotExists("Vanness", UserRole.ADMIN, "Vanness123", "Vanness456");
    }

    private void createUserIfNotExists(String fullName, UserRole managementLevel, String username, String password) {
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
                .isWorking(true)
                .build();

        userRepository.save(user);
        log.info("{} has been added to the system", username);
    }

    @Override
    public GetUserPaginationResponse getUsersByPage(User user, int page, int size, boolean includeInactive) {
        roleSecurityService.checkRole(user, "ROLE_MANAGER");

        Pageable pageable = PageRequest.of(page, size, Sort.by("fullName").ascending());
        Page<User> users;

        if (user.getManagementLevel() == UserRole.OWNER) {
            users = includeInactive
                    ? userRepository.findAll(pageable)
                    : userRepository.findAllByIsWorking(true, pageable);
        } else {
            List<UserRole> visibleRoles = List.of(UserRole.MANAGER, UserRole.ADMIN);
            users = includeInactive
                    ? userRepository.findAllByManagementLevelIn(visibleRoles, pageable)
                    : userRepository.findAllByManagementLevelInAndIsWorking(visibleRoles, true, pageable);
        }

        List<GetUserResponse> userResponses = users.getContent().stream()
                .map(u -> modelMapper.map(u, GetUserResponse.class))
                .collect(Collectors.toList());

        return GetUserPaginationResponse.builder()
                .getUserResponseList(userResponses)
                .page(page)
                .size(size)
                .totalPages(users.getTotalPages())
                .build();
    }

    @Override
    public Boolean changeUserStatus(User user, UUID userId) {
        roleSecurityService.checkRole(user, "ROLE_MANAGER");

        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserExceptions.UserNotFound("User with ID: " + userId + " not found"));

        if (user.getManagementLevel() == UserRole.MANAGER && targetUser.getManagementLevel() == UserRole.OWNER) {
                throw new UserExceptions.UnauthorizedRole("Managers cannot change the status of Owners.");
        }

        targetUser.setWorking(!targetUser.isWorking());
        userRepository.save(targetUser);

        return Boolean.TRUE;
    }

    @Override
    public Boolean addUser(User user, CreateUserRequest createUserRequest) {
        roleSecurityService.checkRole(user, "ROLE_MANAGER");
        if (user.getManagementLevel() == UserRole.MANAGER && createUserRequest.getManagementLevel() == UserRole.OWNER) {
            throw new UserExceptions.UnauthorizedRole("Managers cannot create owner.");
        }

        if (userRepository.existsByUsername(createUserRequest.getUsername())) {
            throw new UserExceptions.UsernameAlreadyExists("Username '" + createUserRequest.getUsername() + "' is already taken.");
        }

        User newUser = modelMapper.map(createUserRequest, User.class);
        newUser.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        newUser.setWorking(true);
        userRepository.save(newUser);

        return Boolean.TRUE;
    }
}
