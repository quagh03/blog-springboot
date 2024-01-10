package org.quagh.blogbackend.services.post;

import org.quagh.blogbackend.dtos.PostDTO;
import org.quagh.blogbackend.entities.Post;
import org.quagh.blogbackend.exceptions.DataNotFoundException;
import org.quagh.blogbackend.exceptions.PostPublishException;
import org.quagh.blogbackend.responses.PostListResponse;
import org.quagh.blogbackend.responses.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IPostService {
    PostListResponse getAllPosts(String keyword, Long categoryId, Long authorId);

    Post getPostById(Long id) throws DataNotFoundException;

    void deletePost(Long id) throws DataNotFoundException;

    Post addPost(PostDTO postDTO) throws DataNotFoundException;

    Post publishPost(Long postId) throws DataNotFoundException, PostPublishException;

    Post unpublishPost(Long postId) throws DataNotFoundException, PostPublishException;

    Post updatePost(Long id, PostDTO postDTO) throws  DataNotFoundException;
}
