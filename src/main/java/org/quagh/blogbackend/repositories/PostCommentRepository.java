package org.quagh.blogbackend.repositories;

import org.quagh.blogbackend.entities.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
