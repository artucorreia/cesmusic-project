package com.blog.cesmusic.data.DTO.v1.output;

import java.time.LocalDateTime;

public class PostCommentDTO {
    private Long id;
    private String content;
    private UserDTO user;
    private PostDTO post;
    private LocalDateTime createdAt;

    public PostCommentDTO() {}

    public PostCommentDTO(Long id, String content, UserDTO user, PostDTO post, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.post = post;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
