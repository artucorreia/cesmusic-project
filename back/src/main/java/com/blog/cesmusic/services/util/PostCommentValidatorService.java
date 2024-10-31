package com.blog.cesmusic.services.util;

import com.blog.cesmusic.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Logger;

@Service
public class PostCommentValidatorService {
    private final Logger LOGGER = Logger.getLogger(PostCommentValidatorService.class.getName());

    private final PostService postService;

    @Autowired
    public PostCommentValidatorService(PostService postService) {
        this.postService = postService;
    }

    public void validateRelationships(UUID postId) {
        LOGGER.info("Validating comment's relationships");
        postService.findById(postId);
    }
}
