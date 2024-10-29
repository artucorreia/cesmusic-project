package com.blog.cesmusic.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "posts")
public class Post implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 200, nullable = false)
    private String summary;

    @Column(name = "cape_image_path")
    private String capeImagePath;

    @Column(length = 20000, nullable = false)
    private String content;

    @ManyToOne @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "post")
    private List<PostComment> comments;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "posts_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Post() {}

    public Post(
            UUID id,
            String title,
            String summary,
            String capeImagePath,
            String content,
            User user,
            List<PostComment> comments,
            List<Tag> tags,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.capeImagePath = capeImagePath;
        this.content = content;
        this.user = user;
        this.comments = comments;
        this.tags = tags;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id)
                && Objects.equals(title, post.title)
                && Objects.equals(summary, post.summary)
                && Objects.equals(capeImagePath, post.capeImagePath)
                && Objects.equals(content, post.content)
                && Objects.equals(user, post.user)
                && Objects.equals(comments, post.comments)
                && Objects.equals(tags, post.tags)
                && Objects.equals(createdAt, post.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, summary, capeImagePath, content, user, comments, tags, createdAt);
    }
}
