package com.blog.cesmusic.controllers;

import com.blog.cesmusic.data.DTO.v1.auth.*;
import com.blog.cesmusic.data.DTO.v1.output.PendingUserDTO;
import com.blog.cesmusic.data.DTO.v1.output.RegisterResponseDTO;
import com.blog.cesmusic.model.User;
import com.blog.cesmusic.services.PendingUserService;
import com.blog.cesmusic.services.auth.TokenService;
import com.blog.cesmusic.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000", "https://musical-blog-cesmac.vercel.app"})
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for registration and login to the system")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;
    private final PendingUserService pendingUserService;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            UserService userService,
            PendingUserService pendingUserService
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
        this.pendingUserService = pendingUserService;
    }

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Log into the system",
            description = "Log into the system",
            tags = {"Authentication"},
            method = "POST",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            { "email*": "string", "password*": "string" }
                                            """
                            )
                    )
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TokenDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            }
    )
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody AuthenticationDTO data) {
        userService.checkUserActivity(data);
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        Authentication auth = authenticationManager.authenticate(usernamePassword);
        TokenDTO token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(token);
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Register in the system",
            description = "Register in the system",
            tags = {"Authentication"},
            method = "POST",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                                            { "name*": "string", "email*": "string", "password*": "string" }
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
                                    schema = @Schema(implementation = PendingUserDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            }
    )
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterDTO data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pendingUserService.register(data));
    }

    @PutMapping(
            value = "/validate-code/{code}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Validate a login code",
            description = "Validate a login code",
            tags = {"Authentication"},
            method = "PUT"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegisterResponseDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content),
            }
    )
    public ResponseEntity<RegisterResponseDTO> validateCode(@PathVariable String code) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pendingUserService.validateCode(code));
    }
}
