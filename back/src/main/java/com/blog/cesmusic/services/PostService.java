package com.blog.cesmusic.services;

import com.blog.cesmusic.controllers.PostController;
import com.blog.cesmusic.controllers.TagController;
import com.blog.cesmusic.data.DTO.v1.create.PostCreateDTO;
import com.blog.cesmusic.data.DTO.v1.output.CreateResponseDTO;
import com.blog.cesmusic.data.DTO.v1.output.PostDTO;
import com.blog.cesmusic.data.DTO.v1.output.TagDTO;
import com.blog.cesmusic.exceptions.general.ResourceNotFoundException;
import com.blog.cesmusic.mapper.Mapper;
import com.blog.cesmusic.model.Post;
import com.blog.cesmusic.projections.PostListProjection;
import com.blog.cesmusic.repositories.PostRepository;
import com.blog.cesmusic.services.auth.TokenService;
import com.blog.cesmusic.services.util.PostValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PostService {
    private final Logger LOGGER = Logger.getLogger(PostService.class.getName());

    private final PostRepository repository;
    private final TagService tagService;
    private final TokenService tokenService;
    private final UserService userService;
    private final PostValidatorService postValidatorService;
    private final Mapper mapper;
    private final PagedResourcesAssembler<PostDTO> assembler;

    @Autowired
    public PostService(
            PostRepository repository,
            TagService tagService,
            TokenService tokenService,
            UserService userService,
            PostValidatorService postValidatorService,
            Mapper mapper,
            PagedResourcesAssembler<PostDTO> assembler
    ) {
        this.repository = repository;
        this.tagService = tagService;
        this.tokenService = tokenService;
        this.userService = userService;
        this.postValidatorService = postValidatorService;
        this.mapper = mapper;
        this.assembler = assembler;
    }

    public PostDTO findById(UUID id) {
        LOGGER.info("Finding post by id");
        PostDTO post = mapper.map(
                repository.findPostById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("Nenhum post encontrado para este id (%s)", id))),
                PostDTO.class
        );
        post.setTags(tagService.findByPostId(id));
        post.add(linkTo(methodOn(PostController.class).findById(post.getId())).withSelfRel());
        return post;
    }

    public PagedModel<EntityModel<PostDTO>> findByTagId(UUID id, Pageable pageable) {
        LOGGER.info("Finding posts by tag id");
        Page<PostListProjection> postList = repository.findByTagId(id, pageable);
        Page<PostDTO> posts = postList.map(
                projection -> mapper.map(projection, PostDTO.class)
                        .add(
                                linkTo(
                                        methodOn(PostController.class).findById(projection.getId())
                                ).withSelfRel()
                        )
        );
        return assembler.toModel(
                posts,
                linkTo(methodOn(TagController.class)
                        .findPosts(
                                id,
                                pageable.getPageNumber(),
                                pageable.getPageSize(),
                                pageable.getSort().toString()
                        )).withSelfRel()
        );
    }

    @Transactional(rollbackFor = Exception.class)
    public CreateResponseDTO<UUID> create(PostCreateDTO postCreateDTO) {
        LOGGER.info("Creating a new post");
        postCreateDTO.setUser(userService.findById(tokenService.getUserId()));
        postValidatorService.validateRelationships(postCreateDTO.getTags().stream().map(TagDTO::getId).toList());
        Post entity = mapper.map(postCreateDTO, Post.class);
        PostDTO post = mapper.map(repository.save(entity), PostDTO.class);
        return new CreateResponseDTO<>(post.getId(), "Post criado com sucesso");
    }
}
