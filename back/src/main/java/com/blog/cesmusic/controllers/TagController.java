package com.blog.cesmusic.controllers;

import com.blog.cesmusic.data.DTO.v1.output.PostDTO;
import com.blog.cesmusic.services.PostService;
import com.blog.cesmusic.services.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000", "https://musical-blog-cesmac.vercel.app"})
@RestController
@RequestMapping("/api/v1/tags")
@Tag(name = "Tag", description = "Endpoints for manage tags")
public class TagController {
    private final TagService service;
    private final PostService postService;

    @Autowired
    public TagController(TagService service, PostService postService) {
        this.service = service;
        this.postService = postService;
    }

    @GetMapping(
            value = "/{id}/posts",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Find posts by tag id",
            description = "Find posts by tag id",
            tags = {"Post", "Tag"},
            method = "GET"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            arraySchema = @Schema(implementation = PostDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            }
    )
    public ResponseEntity<PagedModel<EntityModel<PostDTO>>> findPosts(
            @PathVariable UUID id,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "createdAt"));
        return ResponseEntity.ok(postService.findByTagId(id, pageable));
    }
}
