package org.quagh.blogbackend.repositories;

import org.quagh.blogbackend.entities.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
}
