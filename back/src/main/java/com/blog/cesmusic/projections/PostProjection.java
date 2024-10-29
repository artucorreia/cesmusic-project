package com.blog.cesmusic.projections;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PostProjection {
    UUID getId();
    String getTitle();
    String getSummary();
    String getContent();
    UserProjection getUser();
    LocalDateTime getCreatedAt();
}
