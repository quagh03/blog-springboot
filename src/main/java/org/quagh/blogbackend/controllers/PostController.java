package org.quagh.blogbackend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.dtos.PostDTO;
import org.quagh.blogbackend.entities.Post;
import org.quagh.blogbackend.responses.PostListResponse;
import org.quagh.blogbackend.responses.PostResponse;
import org.quagh.blogbackend.services.post.PostService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("")
    public ResponseEntity<?> getAllPosts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "category_id") Long categoryId,
            @RequestParam(defaultValue = "0", name = "author_id") Long authorId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int limit){
        PostListResponse postListResponse = postService.getAllPosts(keyword, categoryId, authorId);

        //Apply pagination
        int totalItems = postListResponse.getPosts().size();
        int totalPages = (int) Math.ceil((double) totalItems / limit);

        //Calculate start and indexes based on page and limit
        int startIndex = (page - 1) * limit;
        int endIndex = Math.min(startIndex + limit, totalItems);

        // Get the sublist for the current page
        List<PostResponse> paginatedPosts = postListResponse.getPosts().subList(startIndex, endIndex);

        // Create a new response with the paginated posts
        PostListResponse paginatedResponse = PostListResponse.builder()
                .posts(paginatedPosts)
                .build();

        // Create pagination headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Pagination-Total-Items", String.valueOf(totalItems));
        headers.add("Pagination-Total-Pages", String.valueOf(totalPages));
        headers.add("Pagination-Page", String.valueOf(page));
        headers.add("Pagination-Limit", String.valueOf(limit));

        // Add links for next and previous pages
        if (page < totalPages) {
            String nextPageUrl = "/posts?page=" + (page + 1) + "&limit=" + limit + "&category_id=" + categoryId + "&author_id=" + authorId;
            headers.add("Pagination-Next-Page", nextPageUrl);
        }
        if (page > 1) {
            String prevPageUrl = "/posts?page=" + (page - 1) + "&limit=" + limit + "&category_id=" + categoryId + "&author_id=" + authorId;
            headers.add("Pagination-Prev-Page", prevPageUrl);
        }

        return new ResponseEntity<>(paginatedResponse, headers, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id){
        try {
            PostResponse postResponse = PostResponse.fromPost(postService.getPostById(id));
            return ResponseEntity.ok(postResponse);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

//    @GetMapping("/slug/{slug}")
//    public ResponseEntity<?> getPostBySlug(@PathVariable String slug){
//        try {
//            PostResponse postResponse = PostResponse.fromPost();
//            return ResponseEntity.ok(postResponse);
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

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
