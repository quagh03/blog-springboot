package org.quagh.blogbackend.services.post;

import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.entities.Post;
import org.quagh.blogbackend.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService{
    private final PostRepository postRepository;

    @Override
    public Page<Post> getAllPosts(PageRequest pageRequest){
        return null;
    }
}
