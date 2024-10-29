package com.blog.cesmusic.projections;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ProfileProjection {
    UUID getId();
    String getName();
    String getAbout();
    LocalDateTime getCreatedAt();
}
