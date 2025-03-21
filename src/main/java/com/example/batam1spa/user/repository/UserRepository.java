package com.example.batam1spa.user.repository;

import com.example.batam1spa.user.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.batam1spa.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    List<User> findAllByIsWorking(boolean isWorking);

    List<User> findAllByManagementLevelIn(List<UserRole> roles);

    List<User> findAllByManagementLevelInAndIsWorking(List<UserRole> roles, boolean isWorking);
}