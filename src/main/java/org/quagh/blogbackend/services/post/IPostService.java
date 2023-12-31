package org.quagh.blogbackend.services.post;

import org.quagh.blogbackend.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IPostService {
    Page<Post> getAllPosts(PageRequest pageRequest);
}
