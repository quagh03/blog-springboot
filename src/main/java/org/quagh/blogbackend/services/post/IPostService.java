package org.quagh.blogbackend.services.post;

import org.quagh.blogbackend.dtos.PostDTO;
import org.quagh.blogbackend.entities.Post;
import org.quagh.blogbackend.exceptions.DataNotFoundException;
import org.quagh.blogbackend.exceptions.PostPublishException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IPostService {
    Page<Post> getAllPosts(PageRequest pageRequest);

    Post getPostById(Long id) throws DataNotFoundException;

    void deletePost(Long id) throws DataNotFoundException;

    Post addPost(PostDTO postDTO) throws DataNotFoundException;

    Post publishPost(Long postId) throws DataNotFoundException, PostPublishException;

    Post unpublishPost(Long postId) throws DataNotFoundException, PostPublishException;

    Post updatePost(Long id, PostDTO postDTO) throws  DataNotFoundException;
}
