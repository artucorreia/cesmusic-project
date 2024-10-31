package com.blog.cesmusic.repositories;

import com.blog.cesmusic.projections.UserProjection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PostCommentProjection {
    String getContent();
    UserProjection getUser();
    UUID getPostId();
    LocalDateTime getCreatedAt();
}
