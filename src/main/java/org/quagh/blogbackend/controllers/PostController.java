package org.quagh.blogbackend.controllers;

import jakarta.validation.Valid;
import org.quagh.blogbackend.dtos.PostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    @GetMapping("")
    public ResponseEntity<?> getAllPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit){
        return ResponseEntity.ok(String.format("getAllPost, page=%d, limit=%d", page,limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable UUID id){
        return ResponseEntity.ok("Post with id: " + id);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getPostBySlug(@PathVariable String slug){
        return ResponseEntity.ok("Post with id: " + slug);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable UUID id){
        return ResponseEntity.ok("Post with slug = " + id + " deleted successfully");
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
            return ResponseEntity.ok("Post created successfully " + postDTO);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
