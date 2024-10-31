package com.blog.cesmusic.services;

import com.blog.cesmusic.data.DTO.v1.create.PostCommentCreateDTO;
import com.blog.cesmusic.data.DTO.v1.output.CreateResponseDTO;
import com.blog.cesmusic.data.DTO.v1.output.PostCommentDTO;
import com.blog.cesmusic.mapper.Mapper;
import com.blog.cesmusic.model.PostComment;
import com.blog.cesmusic.repositories.PostCommentRepository;
import com.blog.cesmusic.services.auth.TokenService;
import com.blog.cesmusic.services.util.PostCommentValidatorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class PostCommentService {
    private final Logger LOGGER = Logger.getLogger(PostCommentService.class.getName());

    private final PostCommentRepository repository;
    private final TokenService tokenService;
    private final UserService userService;
    private final PostCommentValidatorService postCommentValidatorService;
    private final Mapper mapper;

    @Autowired
    public PostCommentService(
            PostCommentRepository repository,
            TokenService tokenService,
            UserService userService,
            PostCommentValidatorService postCommentValidatorService,
            Mapper mapper
    ) {
        this.repository = repository;
        this.tokenService = tokenService;
        this.userService = userService;
        this.postCommentValidatorService = postCommentValidatorService;
        this.mapper = mapper;
    }

    public List<PostCommentDTO> findByPostId(UUID postId) {
        LOGGER.info(String.format("Finding comments by post id (%s)", postId));
        postCommentValidatorService.validateRelationships(postId);
        return mapper.map(repository.findByPostId(postId), PostCommentDTO.class);
    }

    @Transactional
    public CreateResponseDTO<Long> create(PostCommentCreateDTO comment) {
        LOGGER.info("Creating a post comment");
        postCommentValidatorService.validateRelationships(comment.getPost().getId());
        comment.setUser(userService.findById(tokenService.getUserId()));
        PostComment entity = mapper.map(comment, PostComment.class);
        PostCommentDTO result = mapper.map(repository.save(entity), PostCommentDTO.class);
        return new CreateResponseDTO<>(result.getId(), "Comentário criado com sucesso");
    }
}
