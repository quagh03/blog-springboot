package org.quagh.blogbackend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.dtos.PostDTO;
import org.quagh.blogbackend.entities.Post;
import org.quagh.blogbackend.repositories.PostRepository;
import org.quagh.blogbackend.services.post.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("")
    public ResponseEntity<?> getAllPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit){
        return ResponseEntity.ok(String.format("getAllPost, page=%d, limit=%d", page,limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(postService.getPostById(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getPostBySlug(@PathVariable String slug){
        return ResponseEntity.ok("Post with id: " + slug);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        try {
            postService.deletePost(id);
            return ResponseEntity.ok("Delete post with id: " + id +  " successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/publish/{id}")
    public ResponseEntity<?> publishPost(@PathVariable Long id){
        try {
            Post publishedPost = postService.publishPost(id);
            return ResponseEntity.ok("Published post with id:" + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("unpublish/{id}")
    public ResponseEntity<?> unpublishPost(@PathVariable Long id){
        try {
            Post publishedPost = postService.unpublishPost(id);
            return ResponseEntity.ok("Unpublished post with id:" + id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createPost(
            @Valid @RequestBody PostDTO postDTO,
            BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessage = result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            return ResponseEntity.ok(postService.addPost(postDTO));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @Valid @RequestBody PostDTO postDTO,
            @PathVariable Long id,
            BindingResult result){
        try {
            if(result.hasErrors()){
                List<String> errorMessage = result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            return ResponseEntity.ok(postService.updatePost(id,postDTO));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
