package com.example.batam1spa.log.model;

import com.example.batam1spa.user.model.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_log")
public class AdminLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private UserRole userRole;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private LogType type;

    @Column(nullable = false)
    private String details;
}
