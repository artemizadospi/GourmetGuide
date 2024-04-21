package com.pweb.gourmetguide.repository;

import com.pweb.gourmetguide.model.UserComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<UserComment, Integer> {
    Page<UserComment> getAllCommentsByPostId(Integer postId, Pageable pageable);
}
