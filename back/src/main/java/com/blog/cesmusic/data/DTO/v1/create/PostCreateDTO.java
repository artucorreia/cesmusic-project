package com.blog.cesmusic.data.DTO.v1.create;

import com.blog.cesmusic.data.DTO.v1.output.TagDTO;
import com.blog.cesmusic.data.DTO.v1.output.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public class PostCreateDTO {

    @NotNull @NotBlank @Size(min = 5, max = 50)
    private String title;

    @NotNull @NotBlank @Size(min = 5, max = 200)
    private String summary;

    @Size(max = 255)
    private String capeImagePath;

    @NotNull @NotBlank @Size(min = 30, max = 20000)
    private String content;

    @NotNull
    private UserDTO user;

    @NotNull @NotEmpty
    private List<TagDTO> tags;

    @JsonIgnore
    private final LocalDateTime createdAt = LocalDateTime.now();

    public PostCreateDTO() {}

    public PostCreateDTO(String title, String summary, String capeImagePath, String content, UserDTO user, List<TagDTO> tags) {
        this.title = title;
        this.summary = summary;
        this.capeImagePath = capeImagePath;
        this.content = content;
        this.user = user;
        this.tags = tags;
    }

    public @NotNull @NotBlank @Size(min = 5, max = 50) String getTitle() {
        return title;
    }

    public void setTitle(@NotNull @NotBlank @Size(min = 5, max = 50) String title) {
        this.title = title.trim();
    }

    public @NotNull @NotBlank @Size(min = 5, max = 200) String getSummary() {
        return summary;
    }

    public void setSummary(@NotNull @NotBlank @Size(min = 5, max = 200) String summary) {
        this.summary = summary.trim();
    }

    public @Size(max = 255) String getCapeImagePath() {
        return capeImagePath;
    }

    public void setCapeImagePath(@Size(max = 255) String capeImagePath) {
        this.capeImagePath = capeImagePath.trim();
    }

    public @NotNull @NotBlank @Size(min = 30, max = 20000) String getContent() {
        return content;
    }

    public void setContent(@NotNull @NotBlank @Size(min = 30, max = 20000) String content) {
        this.content = content.trim();
    }

    public @NotNull UserDTO getUser() {
        return user;
    }

    public void setUser(@NotNull UserDTO user) {
        this.user = user;
    }

    public @NotNull @NotEmpty List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(@NotNull @NotEmpty List<TagDTO> tags) {
        this.tags = tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
