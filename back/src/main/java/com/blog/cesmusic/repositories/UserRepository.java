package com.blog.cesmusic.repositories;

import com.blog.cesmusic.model.User;
import com.blog.cesmusic.model.enums.Role;
import com.blog.cesmusic.projections.ProfileProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    UserDetails findUserDetailsByEmail(String email);

    @Query("""
            SELECT
                u.id AS id,
                u.name AS name,
                u.about AS about,
                u.createdAt AS createdAt
            FROM User u
            WHERE
                u.id = :id AND
                u.active = true
            """)
    Optional<ProfileProjection> findProfileById(UUID id);

    Optional<User> findByEmail(String email);

    List<User> findByActiveIsFalse();

    @Query("SELECT u.email FROM User u WHERE u.role = :role")
    List<String> findEmailsByRole(Role role);
}