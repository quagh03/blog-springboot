package org.quagh.blogbackend.controllers;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.quagh.blogbackend.dtos.PostCommentDTO;
import org.quagh.blogbackend.entities.PostComment;
import org.quagh.blogbackend.responses.PostCommentResponse;
import org.quagh.blogbackend.services.comment.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/comments")
@AllArgsConstructor
public class PostCommentController {
    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<?> addComment(
            @Valid @RequestBody PostCommentDTO postCommentDTO,
            BindingResult result){
        PostCommentResponse postCommentResponse = new PostCommentResponse();
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            postCommentResponse.setMessage("INSERT_COMMENT_FAILD");
            postCommentResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(postCommentResponse);
        }
        try {
            PostComment addedComment = commentService.addComment(postCommentDTO);
            postCommentResponse.setMessage("INSERT_COMMENT_SUCCESSFULLY");
            postCommentResponse.setPostComment(addedComment);
            return ResponseEntity.ok(addedComment);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Long id){
        PostCommentResponse postCommentResponse = new PostCommentResponse();
        try {
            PostComment postComment = commentService.getCommentById(id);
            postCommentResponse.setMessage("GET_COMMENT_SUCCESSFULLY");
            postCommentResponse.setPostComment(postComment);
            return ResponseEntity.ok(postCommentResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        PostCommentResponse postCommentResponse = new PostCommentResponse();
        try {
            PostComment deletedComment = commentService.deleteComment(id);
            postCommentResponse.setMessage("DELETE_COMMENT_SUCCESSFULLY");
            postCommentResponse.setPostComment(deletedComment);
            return ResponseEntity.ok(postCommentResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long id,
            @Valid @RequestBody PostCommentDTO postCommentDTO,
            BindingResult result){
        try {
            return null;
        }catch (Exception e){
            return null;
        }
    }
}
