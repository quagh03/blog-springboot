package org.quagh.blogbackend.repositories;

import org.quagh.blogbackend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentCategory(Category category);
}
