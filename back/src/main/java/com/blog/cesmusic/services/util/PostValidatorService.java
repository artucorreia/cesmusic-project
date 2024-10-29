package com.blog.cesmusic.services.util;

import com.blog.cesmusic.services.TagService;
import com.blog.cesmusic.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class PostValidatorService {
    private final Logger LOGGER = Logger.getLogger(PostValidatorService.class.getName());

    private final UserService userService;
    private final TagService tagService;

    public PostValidatorService(UserService userService, TagService tagService) {
        this.userService = userService;
        this.tagService = tagService;
    }

    public void validateRelationships(UUID userId, List<UUID> tagIds) {
        LOGGER.info("Validating post's relationships");
        userService.findById(userId);
        for (UUID id : tagIds) {
            tagService.findById(id);
        }
    }
}
