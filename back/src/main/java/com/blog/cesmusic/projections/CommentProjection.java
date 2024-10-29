package com.blog.cesmusic.projections;

import java.time.LocalDateTime;

public interface CommentProjection {
    String getContent();
    UserProjection getUser();
    LocalDateTime getCreatedAt();
}
