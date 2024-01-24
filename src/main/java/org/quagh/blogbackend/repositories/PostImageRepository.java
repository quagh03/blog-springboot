package org.quagh.blogbackend.repositories;

import org.quagh.blogbackend.entities.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    void deleteAllByPostId(Long postid);
    List<PostImage> findAllByPostId(Long postid);
}
