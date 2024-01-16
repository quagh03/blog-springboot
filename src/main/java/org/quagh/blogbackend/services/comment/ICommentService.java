package org.quagh.blogbackend.services.comment;

import org.quagh.blogbackend.dtos.PostCommentDTO;
import org.quagh.blogbackend.entities.PostComment;
import org.quagh.blogbackend.exceptions.DataNotFoundException;

public interface ICommentService {
    PostComment addComment(PostCommentDTO postCommentDTO) throws DataNotFoundException;

    PostComment getCommentById(Long id) throws DataNotFoundException;

    PostComment deleteComment(Long id) throws DataNotFoundException;
}
