package org.quagh.blogbackend.repositories;

import org.quagh.blogbackend.entities.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    void deleteAllByPostId(Long postid);
}
