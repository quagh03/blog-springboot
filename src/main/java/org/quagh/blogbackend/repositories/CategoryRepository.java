package org.quagh.blogbackend.repositories;

import org.quagh.blogbackend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
