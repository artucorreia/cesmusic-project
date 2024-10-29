package com.blog.cesmusic.repositories;

import com.blog.cesmusic.model.PendingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PendingUserRepository extends JpaRepository<PendingUser, Long> {

    Optional<PendingUser> findByEmail(String email);

    Optional<PendingUser> findByCode(String code);

    @Modifying
    void deleteAllByCreatedAtBefore(LocalDateTime expirationTime);
}
