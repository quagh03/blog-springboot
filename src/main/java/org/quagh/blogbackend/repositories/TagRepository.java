package org.quagh.blogbackend.repositories;

import org.quagh.blogbackend.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
