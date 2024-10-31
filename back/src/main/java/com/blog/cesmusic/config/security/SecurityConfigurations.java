package com.blog.cesmusic.config.security;

import com.blog.cesmusic.services.auth.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    private final SecurityFilter securityFilter;
    @Autowired
    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorize -> authorize
                                // hello world
                                .requestMatchers(HttpMethod.GET, "/api/v1/hello-world").permitAll()

                                // auth
                                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/auth/validate-code/{code}").permitAll()

                                // users
                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/{id}").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/activate/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/users/inactive").hasRole("ADMIN")

                                // posts
                                .requestMatchers(HttpMethod.GET, "/api/v1/posts/{id}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/posts").authenticated()

                                // posts comments
                                .requestMatchers(HttpMethod.GET, "/api/v1/post-comments/{postId}").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/post-comments").authenticated()

                                // tags
                                .requestMatchers(HttpMethod.GET, "/api/v1/tags/{id}/posts").permitAll()

                                // swagger
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/swagger-ui.html").permitAll()

                                .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration)
    throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
