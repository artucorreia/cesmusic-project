package com.blog.cesmusic.data.DTO.v1.util;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public class ObjectIdsDTO {
    @NotEmpty
    List<UUID> ids;

    public ObjectIdsDTO(List<UUID> ids) {
        this.ids = ids;
    }

    public @NotEmpty List<UUID> getIds() {
        return ids;
    }
}
