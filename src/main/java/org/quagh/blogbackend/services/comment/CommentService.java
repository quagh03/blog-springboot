package org.quagh.blogbackend.services.comment;

import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.dtos.PostCommentDTO;
import org.quagh.blogbackend.entities.Post;
import org.quagh.blogbackend.entities.PostComment;
import org.quagh.blogbackend.entities.User;
import org.quagh.blogbackend.exceptions.DataNotFoundException;
import org.quagh.blogbackend.repositories.PostCommentRepository;
import org.quagh.blogbackend.repositories.PostRepository;
import org.quagh.blogbackend.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService{
    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public PostComment addComment(PostCommentDTO postCommentDTO) throws DataNotFoundException {
        //Find Comment's User
        User user = userRepository.findById(postCommentDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("No user found with id"));
        //Find Comment's Post
        Post post = postRepository.findById(postCommentDTO.getPostId())
                .orElseThrow(() -> new DataNotFoundException("Post not found"));
        PostComment addComment = PostComment.builder()
                .title(postCommentDTO.getTitle())
                .published(true)
                .createdAt(new Date())
                .publishedAt(new Date())
                .content(postCommentDTO.getContent())
                .user(user)
                .post(post)
                .build();
        //Find Comment's Parent
        if(postCommentDTO.getParentId() != null){
            //Only 2 level (1 Parent has many child), just direct child no grandchildren
            postCommentRepository.findById(postCommentDTO.getParentId())
                    .ifPresent(parentComment -> addComment.setParentComment(
                            parentComment.getParentComment() != null ? parentComment.getParentComment() : parentComment));
        }
        return postCommentRepository.save(addComment);
    }

    @Override
    public PostComment getCommentById(Long id) throws DataNotFoundException{
        return postCommentRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Comment not found with Id: " + id));
    }

    @Override
    @Transactional
    public PostComment deleteComment(Long id) throws DataNotFoundException{
        PostComment deletedComment = postCommentRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Comment found with id: " + id));
        postCommentRepository.delete(deletedComment);
        return deletedComment;
    }
}
