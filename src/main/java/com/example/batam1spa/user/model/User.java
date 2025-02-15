package com.example.batam1spa.user.model;

import com.example.batam1spa.common.model.Auditable;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_users")
public class User extends Auditable implements UserDetails {
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private UserRole managementLevel;

    @Column(
            unique = true,
            nullable = false
    )
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(
            nullable = false,
            length = 60
    )
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return managementLevel.getAllRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
