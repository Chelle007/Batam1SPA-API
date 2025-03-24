package com.example.batam1spa.user.repository;

import com.example.batam1spa.user.model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.batam1spa.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    Page<User> findAllByIsWorking(boolean isWorking, Pageable pageable);
    Page<User> findAllByManagementLevelIn(List<UserRole> roles, Pageable pageable);
    Page<User> findAllByManagementLevelInAndIsWorking(List<UserRole> roles, boolean isWorking, Pageable pageable);
}