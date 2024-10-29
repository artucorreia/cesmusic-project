package com.blog.cesmusic.services.auth;

import com.blog.cesmusic.data.DTO.v1.output.UserDTO;
import com.blog.cesmusic.exceptions.auth.InactiveUserException;
import com.blog.cesmusic.exceptions.auth.InvalidTokenException;
import com.blog.cesmusic.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserService userService;

    @Autowired
    public SecurityFilter(
        TokenService tokenService,
        UserService userService
    ) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = recoverToken(request);

            if (token != null) {
                UUID userId = tokenService.validateToken(token);
                if (userId == null) throw new InvalidTokenException("Token inválido ou expirado");

                UserDTO user = userService.findById(userId);
                if (!user.getActive()) throw new InactiveUserException("Usuário inativo");

                UserDetails userDetails = userService.findUserDetailsByEmail(user.getEmail());

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        }
        catch (InvalidTokenException e) {
            handleException(response, e);
        }
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

    private void handleException(
            HttpServletResponse response,
            InvalidTokenException e
    ) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getWriter().write("{\"timestamp\":\"" + LocalDateTime.now() + "\",\"title\":\"" + e.getMessage() + "\",\"details\":\"Token error\"}");
    }
}
