package com.blog.cesmusic.repositories;

import com.blog.cesmusic.model.PostComment;
import com.blog.cesmusic.projections.CommentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    @Query("""
            SELECT
            pc.content as content,
            pc.user as user,
            pc.createdAt as createdAt
            FROM PostComment pc
            WHERE pc.post.id = :postId
            ORDER BY pc.createdAt DESC
            """)
    List<CommentProjection> findByPostId(UUID postId);
}