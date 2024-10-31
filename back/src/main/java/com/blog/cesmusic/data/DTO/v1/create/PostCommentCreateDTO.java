package com.blog.cesmusic.data.DTO.v1.create;

import com.blog.cesmusic.data.DTO.v1.output.PostDTO;
import com.blog.cesmusic.data.DTO.v1.output.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class PostCommentCreateDTO {

    @NotNull @NotBlank @Size(max = 500)
    private String content;

    @JsonIgnore
    private UserDTO user;

    @NotNull
    private PostDTO post;

    @JsonIgnore
    private LocalDateTime createdAt = LocalDateTime.now();

    public PostCommentCreateDTO() {}

    public PostCommentCreateDTO(String content, UserDTO user, PostDTO post, LocalDateTime createdAt) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.createdAt = createdAt;
    }

    public @NotNull @NotBlank @Size(max = 500) String getContent() {
        return content;
    }

    public void setContent(@NotNull @NotBlank @Size(max = 500) String content) {
        this.content = content;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public @NotNull PostDTO getPost() {
        return post;
    }

    public void setPost(@NotNull PostDTO post) {
        this.post = post;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}