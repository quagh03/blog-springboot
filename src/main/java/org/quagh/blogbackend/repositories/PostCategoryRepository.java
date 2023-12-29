package org.quagh.blogbackend.repositories;

import org.quagh.blogbackend.entities.Category;
import org.quagh.blogbackend.entities.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    List<PostCategory> findByCategory(Category category);

}
