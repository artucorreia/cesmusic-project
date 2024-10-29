package com.blog.cesmusic.data.DTO.v1.output;

import com.blog.cesmusic.model.enums.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private Role role;
    private String about;
    private LocalDateTime createdAt;
    private Boolean active;

    public UserDTO() {}

    public UserDTO(UUID id, String name, String email, Role role, String about, LocalDateTime createdAt, Boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.about = about;
        this.createdAt = createdAt;
        this.active = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
