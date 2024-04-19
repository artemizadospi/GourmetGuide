package com.pweb.gourmetguide.repository;

import com.pweb.gourmetguide.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post getPostsById(Integer id);
    void deleteById(Integer id);
    Page<Post> findAllByIdIn(List<Integer> ids, Pageable pageable);
    Page<Post> findByTitleContaining(String title, Pageable pageable);
}
