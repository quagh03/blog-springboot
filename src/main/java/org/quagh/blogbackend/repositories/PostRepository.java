package org.quagh.blogbackend.repositories;

import org.quagh.blogbackend.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorId(Long authorId);
    boolean existsByTitle(String title);
    List<Post> findByCategoryId(Long categoryId);
    List<Post> findByTagsId(Long tagId);
//    @Query("SELECT p FROM Post p WHERE " +
//            "(:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
//            "AND (:keyword IS NULL OR :keyword='' OR p.title LIKE %:keyword% OR p.content LIKE %:keyword%) " +
//            "AND (:authorId IS NULL OR :authorId = 0 OR p.author.id = :authorId)"
//    )
//    List<Post> searchPosts(
//            @Param("category_id") Long categoryId,
//            @Param("keyword") String keyword,
//            @Param("author_id") Long authorId
//    );
    @Query("SELECT p FROM Post p WHERE " +
            "(:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword='' OR p.title LIKE %:keyword% OR p.content LIKE %:keyword%) " +
            "AND (:authorId IS NULL OR :authorId = 0 OR p.author.id = :authorId)"
    )
    List<Post> searchPosts(
            @Param("categoryId") Long categoryId,
            @Param("keyword") String keyword,
            @Param("authorId") Long authorId
    );

}
