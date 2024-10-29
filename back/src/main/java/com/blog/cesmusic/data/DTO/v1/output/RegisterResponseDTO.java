package com.blog.cesmusic.data.DTO.v1.output;

import com.blog.cesmusic.model.enums.Role;

import java.time.LocalDateTime;

public class RegisterResponseDTO {
    private String name;
    private String email;
    private Role role;
    private LocalDateTime createdAt;

    public RegisterResponseDTO() {}

    public RegisterResponseDTO(String name, String email, Role role, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
