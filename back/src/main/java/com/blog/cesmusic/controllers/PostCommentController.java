package com.blog.cesmusic.controllers;

import com.blog.cesmusic.data.DTO.v1.create.PostCommentCreateDTO;
import com.blog.cesmusic.data.DTO.v1.output.CreateResponseDTO;
import com.blog.cesmusic.data.DTO.v1.output.PostCommentDTO;
import com.blog.cesmusic.services.PostCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000", "https://musical-blog-cesmac.vercel.app"})
@RestController
@RequestMapping("/api/v1/post-comments")
@Tag(name = "Post Comment", description = "Endpoints to manage post comments")
public class PostCommentController {
    private final PostCommentService service;

    @Autowired
    public PostCommentController(PostCommentService service) {
        this.service = service;
    }

    @GetMapping(
            value = "/{postId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Find comments post by id",
            description = "Find comments post by id",
            tags = {"Post Comment"},
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
                                            schema = @Schema(implementation = PostCommentDTO.class)
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            }
    )
    public ResponseEntity<List<PostCommentDTO>> findByPostId(@PathVariable UUID postId) {
        return ResponseEntity.ok(service.findByPostId(postId));
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Create a comment",
            description = "Create a comment",
            tags = {"Post Comment"},
            method = "POST",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            { "content*": "string", "post*": {"id": "uuid"}, "user*": {"id": "uuid"} }
                                            """
                            )
                    )
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreateResponseDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            }
    )
    public ResponseEntity<CreateResponseDTO<Long>> create(@Valid @RequestBody PostCommentCreateDTO commentCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(commentCreateDTO));
    }
}
