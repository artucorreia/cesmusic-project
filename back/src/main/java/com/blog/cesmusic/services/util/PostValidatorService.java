package com.blog.cesmusic.services.util;

import com.blog.cesmusic.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class PostValidatorService {
    private final Logger LOGGER = Logger.getLogger(PostValidatorService.class.getName());

    private final TagService tagService;

    @Autowired
    public PostValidatorService(TagService tagService) {
        this.tagService = tagService;
    }

    public void validateRelationships(List<UUID> tagIds) {
        LOGGER.info("Validating post's relationships");
        for (UUID id : tagIds) {
            tagService.findById(id);
        }
    }
}
