package com.blog.cesmusic.data.DTO.v1.output;

import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PostDTO extends RepresentationModel<PostDTO> {
    private UUID id;
    private String title;
    private String summary;
    private String capeImagePath;
    private String content;
    private UserDTO user;
    private List<TagDTO> tags;
    private List<PostCommentDTO> comments;
    private LocalDateTime createdAt;

    public PostDTO() {}

    public PostDTO(
            UUID id,
            String title,
            String summary,
            String capeImagePath,
            String content,
            UserDTO user,
            List<TagDTO> tags,
            List<PostCommentDTO> comments,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.capeImagePath = capeImagePath;
        this.content = content;
        this.user = user;
        this.tags = tags;
        this.comments = comments;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCapeImagePath() {
        return capeImagePath;
    }

    public void setCapeImagePath(String capeImagePath) {
        this.capeImagePath = capeImagePath;
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

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public List<PostCommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<PostCommentDTO> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
