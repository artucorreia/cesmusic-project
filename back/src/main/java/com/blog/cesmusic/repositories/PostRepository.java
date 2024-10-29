package com.blog.cesmusic.repositories;

import com.blog.cesmusic.model.Post;
import com.blog.cesmusic.projections.PostListProjection;
import com.blog.cesmusic.projections.PostProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    @Query("""
            SELECT
                p.id AS id,
                p.title AS title,
                p.summary AS summary,
                p.user AS user,
                p.createdAt AS createdAt
            FROM Post p JOIN p.tags t
            WHERE t.id = :id
            """)
    Page<PostListProjection> findByTagId(UUID id, Pageable pageable);

    // List<PostListProjection> findByUserId(UUID id);

    @Query("""
            SELECT
                p.id AS id,
                p.title AS title,
                p.summary AS summary,
                p.content AS content,
                p.user AS user,
                p.createdAt AS createdAt
            FROM Post p
            WHERE p.id = :id
            """)
    Optional<PostProjection> findPostById(UUID id);
}