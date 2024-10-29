package com.blog.cesmusic.projections;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PostListProjection {
    UUID getId();
    String getTitle();
    String getSummary();
    UserProjection getUser();
    LocalDateTime getCreatedAt();
}
