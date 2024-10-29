package com.blog.cesmusic.data.DTO.v1.auth;

import com.blog.cesmusic.model.enums.Role;

import java.time.Instant;
import java.util.UUID;

public class TokenDTO {

    private UUID userId;
    private Role userRole;
    private String token;
    private Instant createdAt;
    private Instant expiresAt;

    public TokenDTO() {}

    public TokenDTO(UUID userId, Role userRole, String token, Instant createdAt, Instant expiresAt) {
        this.userId = userId;
        this.userRole = userRole;
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public UUID getUserId() {
        return userId;
    }

    public Role getUserRole() {
        return userRole;
    }

    public String getToken() {
        return token;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}