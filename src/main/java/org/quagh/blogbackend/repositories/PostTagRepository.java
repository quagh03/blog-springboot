package org.quagh.blogbackend.repositories;

import org.quagh.blogbackend.entities.PostTag;
import org.quagh.blogbackend.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    List<PostTag> findPostTagByTag(Tag tag);
}
