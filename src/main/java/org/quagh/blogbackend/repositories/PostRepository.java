package org.quagh.blogbackend.repositories;

import org.quagh.blogbackend.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorId(Long authorId);
    boolean existsByTitle(String title);
    Page<Post> findAll(Pageable pageable);
    List<Post> findByCategoryId(Long categoryId);
    List<Post> findByTagsId(Long tagId);
}
