package com.blog.cesmusic.repositories;

import com.blog.cesmusic.model.Tag;
import com.blog.cesmusic.projections.TagPostsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    @Query(nativeQuery = true, value = """
            SELECT
            t.id as id,
            t.name as name
            FROM posts_tags pt, tags t
            WHERE pt.post_id = :id and t.id = pt.tag_id;
            """)
    List<TagPostsProjection> findByPostId(UUID id);
}